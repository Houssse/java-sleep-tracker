package ru.yandex.practicum.sleeptracker.function;

import ru.yandex.practicum.sleeptracker.Chronotype;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ChronotypeFunction
        implements Function<List<SleepingSession>, SleepAnalysisResult> {

    private static final LocalTime NIGHT_START = LocalTime.MIDNIGHT;
    private static final LocalTime NIGHT_END = LocalTime.of(6, 0);

    private static final LocalTime OWL_SLEEP_TIME = LocalTime.of(23, 0);
    private static final LocalTime OWL_WAKE_TIME = LocalTime.of(9, 0);

    private static final LocalTime LARK_SLEEP_TIME = LocalTime.of(22, 0);
    private static final LocalTime LARK_WAKE_TIME = LocalTime.of(7, 0);

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        Map<Chronotype, Long> counts = sessions.stream()
                .filter(this::isNightSession)
                .map(this::defineChronotype)
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ));

        Chronotype chronotype = findChronotype(counts);

        return new SleepAnalysisResult(
                "Хронотип пользователя",
                chronotype
        );
    }

    private boolean isNightSession(SleepingSession session) {
        LocalDate startDate = session.getStartSleeping().toLocalDate();

        return intersectsNight(
                session,
                startDate
        ) || intersectsNight(
                session,
                startDate.plusDays(1)
        );
    }

    private boolean intersectsNight(
            SleepingSession session,
            LocalDate date) {

        LocalDateTime nightStart = LocalDateTime.of(
                date,
                NIGHT_START
        );

        LocalDateTime nightEnd = LocalDateTime.of(
                date,
                NIGHT_END
        );

        return session.getStartSleeping().isBefore(nightEnd)
                && session.getEndSleeping().isAfter(nightStart);
    }

    private Chronotype defineChronotype(SleepingSession session) {
        LocalTime sleepTime =
                session.getStartSleeping().toLocalTime();

        LocalTime wakeTime =
                session.getEndSleeping().toLocalTime();

        if (sleepTime.isAfter(OWL_SLEEP_TIME)
                && wakeTime.isAfter(OWL_WAKE_TIME)) {
            return Chronotype.OWL;
        }

        if (sleepTime.isBefore(LARK_SLEEP_TIME)
                && wakeTime.isBefore(LARK_WAKE_TIME)) {
            return Chronotype.LARK;
        }

        return Chronotype.PIGEON;
    }

    private Chronotype findChronotype(Map<Chronotype, Long> counts) {
        long owls = counts.getOrDefault(
                Chronotype.OWL,
                0L
        );

        long larks = counts.getOrDefault(
                Chronotype.LARK,
                0L
        );

        long pigeons = counts.getOrDefault(
                Chronotype.PIGEON,
                0L
        );

        if (owls > larks && owls > pigeons) {
            return Chronotype.OWL;
        }

        if (larks > owls && larks > pigeons) {
            return Chronotype.LARK;
        }

        return Chronotype.PIGEON;
    }
}