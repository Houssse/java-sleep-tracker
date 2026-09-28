package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.function.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.function.SleeplessNightsFunction;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SleeplessNightsFunctionTest {

    private final SleeplessNightsFunction function =
            new SleeplessNightsFunction();

    @Test
    void nightSleep_isNotSleepless() {
        List<SleepingSession> sessions = List.of(
                session(
                        LocalDateTime.of(2025, 10, 1, 23, 0),
                        LocalDateTime.of(2025, 10, 2, 7, 0)
                )
        );

        SleepAnalysisResult result = function.apply(sessions);

        assertEquals(0L, result.getValue());
    }

    @Test
    void sleepFromTwoToSeven_isNotSleepless() {
        List<SleepingSession> sessions = List.of(
                session(
                        LocalDateTime.of(2025, 10, 2, 2, 0),
                        LocalDateTime.of(2025, 10, 2, 7, 0)
                )
        );

        SleepAnalysisResult result = function.apply(sessions);

        assertEquals(0L, result.getValue());
    }

    @Test
    void daytimeSleep_isSleeplessNight() {
        List<SleepingSession> sessions = List.of(
                session(
                        LocalDateTime.of(2025, 10, 2, 7, 0),
                        LocalDateTime.of(2025, 10, 2, 11, 0)
                )
        );

        SleepAnalysisResult result = function.apply(sessions);

        assertEquals(1L, result.getValue());
    }

    @Test
    void severalNights_countsOnlySleeplessOnes() {
        List<SleepingSession> sessions = List.of(
                session(
                        LocalDateTime.of(2025, 10, 1, 23, 0),
                        LocalDateTime.of(2025, 10, 2, 7, 0)
                ),
                session(
                        LocalDateTime.of(2025, 10, 2, 14, 0),
                        LocalDateTime.of(2025, 10, 2, 15, 0)
                ),
                session(
                        LocalDateTime.of(2025, 10, 3, 23, 0),
                        LocalDateTime.of(2025, 10, 4, 6, 0)
                )
        );

        SleepAnalysisResult result = function.apply(sessions);

        assertEquals(1L, result.getValue());
    }

    @Test
    void emptyList_returnsZero() {
        SleepAnalysisResult result = function.apply(List.of());

        assertEquals(0L, result.getValue());
    }

    @Test
    void nightsAcrossMonthBoundary_areCountedCorrectly() {
        List<SleepingSession> sessions = List.of(
                session(
                        LocalDateTime.of(2025, 10, 30, 23, 0),
                        LocalDateTime.of(2025, 10, 31, 7, 0)
                ),
                session(
                        LocalDateTime.of(2025, 11, 1, 23, 0),
                        LocalDateTime.of(2025, 11, 2, 7, 0)
                )
        );

        SleepAnalysisResult result = function.apply(sessions);

        assertEquals(1L, result.getValue());
    }

    private SleepingSession session(
            LocalDateTime start,
            LocalDateTime end) {

        return new SleepingSession(
                start,
                end,
                SleepQuality.NORMAL
        );
    }
}