package ru.mirea.pr1.task2;

import java.util.Scanner;
import java.util.concurrent.*;

public class Ex2 {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                System.out.print("Введите число (или 'exit' для выхода): ");
                String userInput = scanner.nextLine();

                if ("exit".equalsIgnoreCase(userInput)) {
                    break;
                }

                int number = Integer.parseInt(userInput);

                Future<Integer> future = executorService.submit(() -> calculateSquare(number));

                int result = future.get();
                System.out.println("Результат: " + result);
            } catch (NumberFormatException e) {
                System.err.println("Неверный формат числа. Пожалуйста, введите целое число.");
            } catch (InterruptedException | ExecutionException e) {
                System.err.println("Ошибка при выполнении запроса: " + e.getMessage());
            }
        }

        executorService.shutdown();
        scanner.close();
    }

    private static int calculateSquare(int number) {
        int delayInSeconds = ThreadLocalRandom.current().nextInt(1, 6);
        try {
            Thread.sleep(delayInSeconds * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return number * number;
    }
}

