package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.function.ChronotypeFunction;
import ru.yandex.practicum.sleeptracker.function.SleepAnalysisResult;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChronotypeFunctionTest {

    private final ChronotypeFunction function =
            new ChronotypeFunction();

    @Test
    void mostlyOwls_returnsOwl() {
        List<SleepingSession> sessions = List.of(
                session(
                        LocalDateTime.of(2025, 10, 1, 23, 30),
                        LocalDateTime.of(2025, 10, 2, 9, 30)
                ),
                session(
                        LocalDateTime.of(2025, 10, 2, 23, 45),
                        LocalDateTime.of(2025, 10, 3, 10, 0)
                ),
                session(
                        LocalDateTime.of(2025, 10, 3, 21, 30),
                        LocalDateTime.of(2025, 10, 4, 6, 30)
                )
        );

        SleepAnalysisResult result = function.apply(sessions);

        assertEquals(Chronotype.OWL, result.getValue());
    }

    @Test
    void mostlyLarks_returnsLark() {
        List<SleepingSession> sessions = List.of(
                session(
                        LocalDateTime.of(2025, 10, 1, 21, 30),
                        LocalDateTime.of(2025, 10, 2, 6, 30)
                ),
                session(
                        LocalDateTime.of(2025, 10, 2, 21, 0),
                        LocalDateTime.of(2025, 10, 3, 6, 0)
                ),
                session(
                        LocalDateTime.of(2025, 10, 3, 23, 30),
                        LocalDateTime.of(2025, 10, 4, 9, 30)
                )
        );

        SleepAnalysisResult result = function.apply(sessions);

        assertEquals(Chronotype.LARK, result.getValue());
    }

    @Test
    void ordinarySleep_returnsPigeon() {
        List<SleepingSession> sessions = List.of(
                session(
                        LocalDateTime.of(2025, 10, 1, 22, 30),
                        LocalDateTime.of(2025, 10, 2, 8, 0)
                )
        );

        SleepAnalysisResult result = function.apply(sessions);

        assertEquals(Chronotype.PIGEON, result.getValue());
    }

    @Test
    void equalNumberOfTypes_returnsPigeon() {
        List<SleepingSession> sessions = List.of(
                session(
                        LocalDateTime.of(2025, 10, 1, 23, 30),
                        LocalDateTime.of(2025, 10, 2, 9, 30)
                ),
                session(
                        LocalDateTime.of(2025, 10, 2, 21, 30),
                        LocalDateTime.of(2025, 10, 3, 6, 30)
                )
        );

        SleepAnalysisResult result = function.apply(sessions);

        assertEquals(Chronotype.PIGEON, result.getValue());
    }

    @Test
    void daytimeSleep_isIgnored() {
        List<SleepingSession> sessions = List.of(
                session(
                        LocalDateTime.of(2025, 10, 1, 14, 0),
                        LocalDateTime.of(2025, 10, 1, 16, 0)
                ),
                session(
                        LocalDateTime.of(2025, 10, 1, 23, 30),
                        LocalDateTime.of(2025, 10, 2, 9, 30)
                )
        );

        SleepAnalysisResult result = function.apply(sessions);

        assertEquals(Chronotype.OWL, result.getValue());
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