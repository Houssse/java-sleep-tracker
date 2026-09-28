package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.function.MinimalSessionFunction;
import ru.yandex.practicum.sleeptracker.function.SleepAnalysisResult;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class MinimalSessionFunctionTest {

    private final MinimalSessionFunction function = new MinimalSessionFunction();

    @Test
    void emptyList_returnsZero() {
        SleepAnalysisResult result = function.apply(List.of());

        assertEquals(0L, result.getValue());
    }

    @Test
    void multipleSessions_returnsMinimal() {
        List<SleepingSession> sessions = List.of(
                session(LocalDateTime.of(2025, 10, 1, 23, 0),
                        LocalDateTime.of(2025, 10, 2, 7, 0)),
                session(LocalDateTime.of(2025, 10, 2, 23, 30),
                        LocalDateTime.of(2025, 10, 3, 6, 0)),
                session(LocalDateTime.of(2025, 10, 3, 22, 0),
                        LocalDateTime.of(2025, 10, 4, 8, 0))
        );

        SleepAnalysisResult result = function.apply(sessions);

        assertEquals(390L, result.getValue());
    }

    @Test
    void sameDurations_returnsSame() {
        List<SleepingSession> sessions = List.of(
                session(LocalDateTime.of(2025, 10, 1, 23, 0),
                        LocalDateTime.of(2025, 10, 2, 7, 0)),
                session(LocalDateTime.of(2025, 10, 2, 23, 0),
                        LocalDateTime.of(2025, 10, 3, 7, 0))
        );

        SleepAnalysisResult result = function.apply(sessions);

        assertEquals(480L, result.getValue());
    }

    @Test
    void returnsCorrectDescription() {
        SleepAnalysisResult result = function.apply(List.of());

        assertEquals("Минимальная продолжительность сессии сна", result.getDescription());
    }

    private SleepingSession session(LocalDateTime start, LocalDateTime end) {
        return new SleepingSession(start, end, SleepQuality.NORMAL);
    }
}