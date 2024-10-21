package ru.mirea.pr1.task1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ForkJoinSumFinder {

    // С использованием многопоточности (Thread, Future, и т. д.).
    public long parallelSum(int[] arr) throws InterruptedException, ExecutionException {
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

    public static long findSumFork(int[] arr) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        SumTask task = new SumTask(arr, 0, arr.length);
        return forkJoinPool.invoke(task);
    }

    // Внутренний класс SumTask, расширяющий RecursiveTask для многопоточного выполнения
    static class SumTask extends RecursiveTask<Integer> {
        private int[] arr;
        private int start;
        private int end;

        SumTask(int[] arr, int start, int end) {
            this.arr = arr;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            if (end - start <= 1000) {
                try {
                    return findSumInRange(arr, start, end);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            int middle = start + (end - start) / 2;

            SumTask leftTask = new SumTask(arr, start, middle);
            SumTask rightTask = new SumTask(arr, middle, end);
            leftTask.fork();

            int rightResult = rightTask.compute();
            int leftResult = leftTask.join();
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return leftResult + rightResult;
        }
    }

    private static int findSumInRange(int[] arr, int start, int end) throws InterruptedException {
        int sum = 0;
        for (int i = start; i < end; i++) {
            sum += arr[i];
        }
        return sum;
    }
}
