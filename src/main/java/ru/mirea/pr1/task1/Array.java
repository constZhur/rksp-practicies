package ru.mirea.pr1.task1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class Array {
    private static final int LENGTH = 1000;
    private int[] arr;

    public Array() {
        this.arr = new int[LENGTH];
        Random random = new Random();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(100);
        }
    }

    public int[] getArr() {
        return arr;
    }
}
