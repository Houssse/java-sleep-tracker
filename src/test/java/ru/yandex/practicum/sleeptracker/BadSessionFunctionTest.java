package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.function.BadSessionFunction;
import ru.yandex.practicum.sleeptracker.function.SleepAnalysisResult;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BadSessionFunctionTest {

    private final BadSessionFunction function = new BadSessionFunction();

    @Test
    void noBadSessions_returnsZero() {
        List<SleepingSession> sessions = List.of(
                session(SleepQuality.GOOD),
                session(SleepQuality.NORMAL)
        );

        SleepAnalysisResult result = function.apply(sessions);

        assertEquals(0, result.getValue());
    }

    @Test
    void oneBadSession_returnsOne() {
        List<SleepingSession> sessions = List.of(
                session(SleepQuality.GOOD),
                session(SleepQuality.BAD),
                session(SleepQuality.NORMAL)
        );

        SleepAnalysisResult result = function.apply(sessions);

        assertEquals(1, result.getValue());
    }

    @Test
    void mixedQualities_countsOnlyBad() {
        List<SleepingSession> sessions = List.of(
                session(SleepQuality.BAD),
                session(SleepQuality.GOOD),
                session(SleepQuality.BAD),
                session(SleepQuality.NORMAL),
                session(SleepQuality.BAD)
        );

        SleepAnalysisResult result = function.apply(sessions);

        assertEquals(3, result.getValue());
    }

    @Test
    void returnsCorrectDescription() {
        SleepAnalysisResult result = function.apply(List.of());

        assertEquals("Количество сессий с плохим качеством сна", result.getDescription());
    }

    private SleepingSession session(SleepQuality quality) {
        return new SleepingSession(
                LocalDateTime.of(2025, 10, 1, 23, 0),
                LocalDateTime.of(2025, 10, 2, 7, 0),
                quality);
    }
}