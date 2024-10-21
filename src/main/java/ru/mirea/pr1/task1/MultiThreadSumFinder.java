package ru.mirea.pr1.task1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class MultiThreadSumFinder {

    public static long parallelSum(int[] arr) throws InterruptedException, ExecutionException {
        int numberOfThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);

        List<Callable<Integer>> tasks = new ArrayList<>();
        int batchSize = arr.length / numberOfThreads;

        for (int i = 0; i < numberOfThreads; i++) {
            final int startIndex = i * batchSize;
            final int endIndex = (i == numberOfThreads - 1) ? arr.length : (i + 1) * batchSize;

            tasks.add(() -> findSumInRange(arr, startIndex, endIndex));
        }
        List<Future<Integer>> futures = executorService.invokeAll(tasks);

        int sum = 0;

        for (Future<Integer> future : futures) {
            sum += future.get();
            Thread.sleep(1);
        }

        executorService.shutdown();

        return sum;
    }

    private static int findSumInRange(int[] arr, int start, int end) throws InterruptedException {
        int sum = 0;
        for (int i = start; i < end; i++) {
            sum += arr[i];
            Thread.sleep(1);
        }
        return sum;
    }
}
