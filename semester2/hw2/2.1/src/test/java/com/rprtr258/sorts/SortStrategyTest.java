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

    protected boolean sortTest(int[] array, SortStrategy sorter) {
        sorter.sort(array);
        return isSorted(array);
    }

    protected boolean randomTest(SortStrategy sorter) {
        Random generator = new Random();
        int[] array = new int[10];
        for (int i = 0; i < array.length; i++)
            array[i] = generator.nextInt();

        return sortTest(array, sorter);
    }

    protected boolean equalsTest(SortStrategy sorter) {
        Random generator = new Random();
        int[] array = new int[20];
        int value = generator.nextInt();
        for (int i = 0; i < array.length; i++)
            array[i] = value;
        sorter.sort(array);
        return isSorted(array);
    }

    protected boolean someEqualsTest(SortStrategy sorter) {
        Random generator = new Random();
        int[] array = new int[20];
        int value = generator.nextInt();
        array[0] = 5;
        array[1] = 20;
        array[2] = 5;
        array[3] = 300;
        array[4] = 20;
        for (int i = 5; i < array.length; i++)
            array[i] = value;
        sorter.sort(array);
        return isSorted(array);
    }
}
