package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.function.AverageSessionFunction;
import ru.yandex.practicum.sleeptracker.function.SleepAnalysisResult;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AverageSessionFunctionTest {

    private final AverageSessionFunction function = new AverageSessionFunction();

    @Test
    void apply_twoSessions_returnsAverage() {
        List<SleepingSession> sessions = List.of(
                session(LocalDateTime.of(2025, 10, 1, 23, 0),
                        LocalDateTime.of(2025, 10, 2, 7, 0)),
                session(LocalDateTime.of(2025, 10, 2, 23, 0),
                        LocalDateTime.of(2025, 10, 3, 5, 0))
        );

        SleepAnalysisResult result = function.apply(sessions);

        assertEquals(420L, result.getValue());
    }

    @Test
    void apply_returnsCorrectDescription() {
        SleepAnalysisResult result = function.apply(List.of());

        assertEquals("Средняя продолжительность сна ", result.getDescription());
    }

    private SleepingSession session(LocalDateTime start, LocalDateTime end) {
        return new SleepingSession(start, end, SleepQuality.NORMAL);
    }
}