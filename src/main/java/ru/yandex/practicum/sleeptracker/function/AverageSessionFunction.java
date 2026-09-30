package ru.yandex.practicum.sleeptracker.function;

import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public class AverageSessionFunction implements Function<List<SleepingSession>, SleepAnalysisResult> {

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        long minutes = (long) sessions.stream()
                .mapToLong(s -> Duration.between(
                        s.getStartSleeping(),
                        s.getEndSleeping()
                ).toMinutes())
                .average()
                .orElse(0.0);

        return new SleepAnalysisResult("Средняя продолжительность сна", minutes);
    }
}