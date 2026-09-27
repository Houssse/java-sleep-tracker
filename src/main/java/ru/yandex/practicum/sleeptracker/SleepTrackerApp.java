package ru.yandex.practicum.sleeptracker;

import ru.yandex.practicum.sleeptracker.function.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.function.TotalSessionFunction;

import java.util.List;
import java.util.function.Function;

public class SleepTrackerApp {
    private final String FILE_NAME = "src/main/resources/sleep_log.txt";
    private final List<Function<List<SleepingSession>, SleepAnalysisResult>> functions = List.of(
            new TotalSessionFunction()
    );

    public static void main(String[] args) {
        SleepTrackerApp app = new SleepTrackerApp();

        List<SleepingSession> sessions = ReadFile.readFile(app.FILE_NAME);

        app.functions.forEach(f -> {
            SleepAnalysisResult result = f.apply(sessions);
            System.out.println(result);
        });
    }
}