package com.rprtr258.sorts;

import java.util.Random;

class SortStrategyTest {
    private static boolean isSorted(int[] array) {
        for (int i = 1; i < array.length; i++) {
            if (array[i - 1] > array[i]) {
                return false;
            }
        }
        return true;
    }

    boolean sortTest(int[] array, SortStrategy sorter) {
        sorter.sort(array);
        return isSorted(array);
    }

    boolean randomTest(SortStrategy sorter) {
        Random generator = new Random();
        int[] array = new int[10];
        for (int i = 0; i < 10; i++)
            array[i] = generator.nextInt();

        return sortTest(array, sorter);
    }
}
