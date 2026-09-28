package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.function.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.function.TotalSessionFunction;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TotalSessionFunctionTest {

    private final TotalSessionFunction function = new TotalSessionFunction();

    @Test
    void emptyList_returnsZero() {
        SleepAnalysisResult result = function.apply(List.of());

        assertEquals(0L, result.getValue());
    }

    @Test
    void oneSession_returnsOne() {
        List<SleepingSession> sessions = List.of(
                session(LocalDateTime.of(2025, 10, 1, 23, 15),
                        LocalDateTime.of(2025, 10, 2, 7, 30))
        );

        SleepAnalysisResult result = function.apply(sessions);

        assertEquals(1L, result.getValue());
    }

    private SleepingSession session(LocalDateTime start, LocalDateTime end) {
        return new SleepingSession(start, end, SleepQuality.NORMAL);
    }
}
