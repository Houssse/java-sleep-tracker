package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReadFileTest {

    @TempDir
    Path tempDir;

    @Test
    void readFile_validFile_returnsSessions() throws IOException {
        Path file = tempDir.resolve("sessions.txt");
        Files.write(file, List.of(
                "01.10.25 23:15;02.10.25 07:30;GOOD",
                "02.10.25 23:50;03.10.25 06:40;NORMAL"
        ));

        List<SleepingSession> sessions = ReadFile.readFile(file.toString());

        assertEquals(2, sessions.size());
    }

    @Test
    void readFile_parsesFieldsCorrectly() throws IOException {
        Path file = tempDir.resolve("one.txt");
        Files.write(file, List.of("01.10.25 23:15;02.10.25 07:30;GOOD"));

        List<SleepingSession> sessions = ReadFile.readFile(file.toString());
        SleepingSession session = sessions.get(0);

        assertEquals(LocalDateTime.of(2025, 10, 1, 23, 15),
                session.getStartSleeping());
        assertEquals(LocalDateTime.of(2025, 10, 2, 7, 30),
                session.getEndSleeping());
        assertEquals(SleepQuality.GOOD, session.getSleepQuality());
    }

    @Test
    void readFile_emptyLines_areSkipped() throws IOException {
        Path file = tempDir.resolve("empty.txt");
        Files.write(file, List.of(
                "01.10.25 23:15;02.10.25 07:30;GOOD",
                "",
                "   ",
                "02.10.25 23:50;03.10.25 06:40;NORMAL"
        ));

        List<SleepingSession> sessions = ReadFile.readFile(file.toString());

        assertEquals(2, sessions.size());
    }

    @Test
    void readFile_missingFile_throwsException() {
        String path = tempDir.resolve("nonexistent.txt").toString();

        assertThrows(IllegalArgumentException.class, () -> ReadFile.readFile(path));
    }

    @Test
    void readFile_emptyFile_returnsEmptyList() throws IOException {
        Path file = tempDir.resolve("empty.txt");
        Files.write(file, List.of());

        List<SleepingSession> sessions = ReadFile.readFile(file.toString());

        assertTrue(sessions.isEmpty());
    }

    @Test
    void readFile_allQualities_parsed() throws IOException {
        Path file = tempDir.resolve("qualities.txt");
        Files.write(file, List.of(
                "01.10.25 23:15;02.10.25 07:30;GOOD",
                "02.10.25 23:50;03.10.25 06:40;NORMAL",
                "03.10.25 23:40;04.10.25 08:00;BAD"
        ));

        List<SleepingSession> sessions = ReadFile.readFile(file.toString());

        assertEquals(SleepQuality.GOOD, sessions.get(0).getSleepQuality());
        assertEquals(SleepQuality.NORMAL, sessions.get(1).getSleepQuality());
        assertEquals(SleepQuality.BAD, sessions.get(2).getSleepQuality());
    }
}