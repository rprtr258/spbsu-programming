package com.rprtr258;

import java.util.Random;

public class Benchmark {
    public static void main(String[] args) {
        final int TESTS = 100;
        testRandom(TESTS, 10);
        testRandom(TESTS, 100);
        testRandom(TESTS, 1000);
        testRandom(TESTS, 10000);
        testRandom(TESTS, 100000);
        testRandom(TESTS, 1000000);
    }

    private static void testRandom(int tests, int size) {
        System.out.printf("%d-size random array test\n", size);
        int tmp = tests;
        double averageTimeSingle = 0;
        double averageTimeMulti = 0;
        while (tmp > 0) {
            int[] array = new int[size];
            Random generator = new Random();
            for (int i = 0; i < array.length; i++)
                array[i] = generator.nextInt();
            int[] arrayCopy = array.clone();
            averageTimeSingle += measureTime(() -> QSorter.sort(array));
            averageTimeMulti += measureTime(() -> ParallelQSorter.sort(arrayCopy));
            tmp--;
        }
        System.out.printf("SingleThread: %.2f ms\n", averageTimeSingle / tests / 1000000);
        System.out.printf("MultiThread: %.2f ms\n", averageTimeMulti / tests / 1000000);
    }

    private static long measureTime(Runnable runnable) {
        long startTime = System.nanoTime();
        runnable.run();
        long endTime = System.nanoTime();
        return endTime - startTime;
    }
}
