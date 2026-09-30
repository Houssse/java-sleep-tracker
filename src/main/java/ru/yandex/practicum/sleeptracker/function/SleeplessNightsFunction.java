package ru.yandex.practicum.sleeptracker.function;

import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

public class SleeplessNightsFunction
        implements Function<List<SleepingSession>, SleepAnalysisResult> {

    private static final LocalTime NOON = LocalTime.NOON;
    private static final LocalTime NIGHT_START = LocalTime.MIDNIGHT;
    private static final LocalTime NIGHT_END = LocalTime.of(6, 0);

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) {
            return new SleepAnalysisResult(
                    "Количество бессонных ночей",
                    0L
            );
        }

        LocalDate firstNight = getFirstNight(sessions);
        LocalDate lastNight = getLastNight(sessions);

        long totalNights = ChronoUnit.DAYS.between(
                firstNight,
                lastNight
        ) + 1;

        long sleeplessNights = IntStream.range(0, (int) totalNights)
                .mapToObj(firstNight::plusDays)
                .filter(night -> !hasSleepDuringNight(sessions, night))
                .count();

        return new SleepAnalysisResult(
                "Количество бессонных ночей",
                sleeplessNights
        );
    }

    private LocalDate getFirstNight(List<SleepingSession> sessions) {
        SleepingSession firstSession = sessions.get(0);

        LocalDate firstDate =
                firstSession.getStartSleeping().toLocalDate();

        if (firstSession.getStartSleeping()
                .toLocalTime()
                .isAfter(NOON)) {

            return firstDate;
        }

        return firstDate.minusDays(1);
    }

    private LocalDate getLastNight(List<SleepingSession> sessions) {
        SleepingSession lastSession =
                sessions.get(sessions.size() - 1);

        return lastSession.getEndSleeping()
                .toLocalDate()
                .minusDays(1);
    }

    private boolean hasSleepDuringNight(
            List<SleepingSession> sessions,
            LocalDate night) {

        LocalDate nightDate = night.plusDays(1);

        LocalDateTime nightStart = LocalDateTime.of(
                nightDate,
                NIGHT_START
        );

        LocalDateTime nightEnd = LocalDateTime.of(
                nightDate,
                NIGHT_END
        );

        return sessions.stream()
                .anyMatch(session ->
                        session.getStartSleeping().isBefore(nightEnd)
                                && session.getEndSleeping().isAfter(nightStart)
                );
    }
}