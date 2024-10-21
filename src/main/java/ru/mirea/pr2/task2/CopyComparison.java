package ru.mirea.pr2.task2;

import java.io.IOException;

public class    CopyComparison {
    public static void main(String[] args) throws IOException {
        // Создание классов
        FileIOStreamCopier copier1 = new FileIOStreamCopier();
        FileChannelCopier copier2 = new FileChannelCopier();
        ApacheCommonsIOCopier copier3 = new ApacheCommonsIOCopier();
        FilesClassCopier copier4 = new FilesClassCopier();

        // Метод 1: FileInputStream/FileOutputStream
        long startTime1 = System.currentTimeMillis();
        copier1.copyFile();
        long endTime1 = System.currentTimeMillis();
        printTimeAndMemoryUsage("FileInputStream/FileOutputStream", startTime1, endTime1);

        // Метод 2: FileChannel
        long startTime2 = System.currentTimeMillis();
        copier2.copyFile();
        long endTime2 = System.currentTimeMillis();
        printTimeAndMemoryUsage("FileChannel", startTime2, endTime2);

        // Метод 3: Apache Commons IO
        long startTime3 = System.currentTimeMillis();
        copier3.copyFile();
        long endTime3 = System.currentTimeMillis();
        printTimeAndMemoryUsage("Apache Commons IO", startTime3, endTime3);

        // Метод 4: Files class
        long startTime4 = System.currentTimeMillis();
        copier4.copyFile();
        long endTime4 = System.currentTimeMillis();
        printTimeAndMemoryUsage("Files class", startTime4, endTime4);
    }

    private static void printTimeAndMemoryUsage(String method, long startTime, long endTime) {
        long elapsedTime = endTime - startTime;
        System.out.println("Метод " + method + ":");
        System.out.println("Время выполнения: " + elapsedTime + " мс");
        Runtime runtime = Runtime.getRuntime();
        long memoryUsed = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Использование памяти: " + memoryUsed / (1024 * 1024)
                + " МБ");
        System.out.println();
    }

}
