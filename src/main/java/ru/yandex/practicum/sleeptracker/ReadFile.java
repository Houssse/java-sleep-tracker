package ru.yandex.practicum.sleeptracker;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public final class ReadFile {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

    private ReadFile() {}

    public static List<SleepingSession> readFile(String fileName) {
        if (!checkPath(fileName)) {
            throw new IllegalArgumentException("Файл не найден: " + fileName);
        }

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8))) {

            return br.lines()
                    .map(String::trim)
                    .filter(line -> !line.isEmpty())
                    .map(ReadFile::parserLine)
                    .collect(Collectors.toList());

        } catch (IOException e) {
            throw new UncheckedIOException("Ошибка чтения файла: " + fileName, e);
        }
    }

    private static SleepingSession parserLine(String line) {
        String[] split = line.split(";");

        LocalDateTime startTime = LocalDateTime.parse(split[0].trim(), FORMATTER);
        LocalDateTime endTime = LocalDateTime.parse(split[1].trim(), FORMATTER);
        SleepQuality sleepQuality = SleepQuality.valueOf(split[2].trim().toUpperCase());

        return new SleepingSession(startTime, endTime, sleepQuality);
    }

    private static boolean checkPath(String fileName) {
        File file = new File(fileName);
        return file.exists() && file.isFile();
    }
}