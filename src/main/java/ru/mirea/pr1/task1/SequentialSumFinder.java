package ru.mirea.pr1.task1;

public class SequentialSumFinder {
    public static long sequentialSum(int[] arr) throws InterruptedException {
        long sum = 0;

        for (int num : arr) {
            sum += num;
            Thread.sleep(1);
        }

        return sum;
    }
}
