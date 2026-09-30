package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.function.MaximumSessionFunction;
import ru.yandex.practicum.sleeptracker.function.SleepAnalysisResult;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MaximumSessionFunctionTest {

    private final MaximumSessionFunction function = new MaximumSessionFunction();

    @Test
    void multipleSessions_returnsMaximum() {
        List<SleepingSession> sessions = List.of(
                session(LocalDateTime.of(2025, 10, 1, 23, 0),
                        LocalDateTime.of(2025, 10, 2, 7, 0)),
                session(LocalDateTime.of(2025, 10, 2, 23, 30),
                        LocalDateTime.of(2025, 10, 3, 6, 0)),
                session(LocalDateTime.of(2025, 10, 3, 22, 0),
                        LocalDateTime.of(2025, 10, 4, 8, 0))
        );

        SleepAnalysisResult result = function.apply(sessions);

        assertEquals(600L, result.getValue());
    }

    @Test
    void returnsCorrectDescription() {
        SleepAnalysisResult result = function.apply(List.of());

        assertEquals("Максимальная продолжительность сессии сна", result.getDescription());
    }

    private SleepingSession session(LocalDateTime start, LocalDateTime end) {
        return new SleepingSession(start, end, SleepQuality.NORMAL);
    }
}