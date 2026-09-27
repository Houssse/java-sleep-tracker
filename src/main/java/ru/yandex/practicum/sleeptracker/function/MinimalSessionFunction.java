package ru.yandex.practicum.sleeptracker.function;

import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public class MinimalSessionFunction implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        long minutes = sessions.stream()
                .mapToLong(s -> Duration.between(s.getStartSleeping(), s.getEndSleeping()).toMinutes())
                .min()
                .orElse(0L);

        return new SleepAnalysisResult("Минимальная продолжительность сессии сна", minutes);
    }
}
