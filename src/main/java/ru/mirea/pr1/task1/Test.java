package ru.mirea.pr1.task1;

import java.util.concurrent.ExecutionException;

public class Test {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Array array = new Array();

        // Тестирование последовательного метода
        long beforeMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        long startTime = System.nanoTime();
        long result = SequentialSumFinder.sequentialSum(array.getArr());
        long endTime = System.nanoTime();
        long afterMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        long durationInMilliseconds = (endTime - startTime) / 1_000_000;
        long memoryUsed = afterMemory - beforeMemory;
        System.out.println("Время выполнения последовательной функции: " +
                durationInMilliseconds + " миллисекунд. Память: " + memoryUsed + " байт. Результат - " + result);

        // Тестирование метода с многопоточностью
        beforeMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        startTime = System.nanoTime();
        result = MultiThreadSumFinder.parallelSum(array.getArr());
        endTime = System.nanoTime();
        afterMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        durationInMilliseconds = (endTime - startTime) / 1_000_000;
        memoryUsed = afterMemory - beforeMemory;
        System.out.println("Время выполнения многопоточной функции: " +
                durationInMilliseconds + " миллисекунд. Память: " + memoryUsed + " байт. Результат - " + result);

        // Тестирование метода с использованием ForkJoin
        beforeMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        startTime = System.nanoTime();
        result = ForkJoinSumFinder.findSumFork(array.getArr());
        endTime = System.nanoTime();
        afterMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        durationInMilliseconds = (endTime - startTime) / 1_000_000;
        memoryUsed = afterMemory - beforeMemory;
        System.out.println("Время выполнения ForkJoin: " +
                durationInMilliseconds + " миллисекунд. Память: " + memoryUsed + " байт. Результат - " + result);
    }
}
