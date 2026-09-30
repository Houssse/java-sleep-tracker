package ru.yandex.practicum.sleeptracker;

import ru.yandex.practicum.sleeptracker.function.*;

import java.util.List;
import java.util.function.Function;

public class SleepTrackerApp {

    private static final List<Function<List<SleepingSession>, SleepAnalysisResult>> functions = List.of(
            new TotalSessionFunction(),
            new MinimalSessionFunction(),
            new MaximumSessionFunction(),
            new AverageSessionFunction(),
            new BadSessionFunction(),
            new SleeplessNightsFunction(),
            new ChronotypeFunction()
    );

    public static void main(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException(
                    "Необходимо указать путь к файлу с логом сна"
            );
        }

        List<SleepingSession> sessions = ReadFile.readFile(args[0]);

        functions.forEach(function -> {
            SleepAnalysisResult result = function.apply(sessions);
            System.out.println(result);
        });
    }
}