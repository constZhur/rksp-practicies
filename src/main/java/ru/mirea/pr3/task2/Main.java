package ru.mirea.pr3.task2;

import io.reactivex.Observable;
import io.reactivex.Single;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {

        Observable<Integer> squares = Observable
                .range(0, 1000)
                .map(number -> number * number);

        Observable<Integer> greaterThan500 = Observable
                .range(0, 1000)
                .filter(number -> number > 500);

        Single<Long> source = Observable
                .fromCallable(() -> {
                    int count = new Random().nextInt(1001);
                    List<Integer> numbers = new ArrayList<>();
                    for (int i = 0; i < count; i++) {
                        numbers.add(new Random().nextInt(1001));
                    }
                    return numbers;
                })
                .flatMapIterable(numbers -> numbers)
                .count();

        squares.subscribe(result -> System.out.println("Square: " + result));
        greaterThan500.subscribe(result -> System.out.println("Number > 500: " + result));
        source.subscribe(count -> System.out.println("Количество сгенерированных чисел: " + count),
                throwable -> System.out.println("Ошибка: " + throwable.getMessage()));
    }
}

