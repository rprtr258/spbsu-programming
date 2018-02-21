package com.rprtr258.sorts;

import org.junit.Test;

import static org.junit.Assert.*;

public class BubbleSortTest {
    private boolean isSorted(int[] array) {
        for (int i = 1; i < array.length; i++) {
            if (array[i - 1] > array[i]) {
                return false;
            }
        }
        return true;
    }

    @Test
    public void sortTest() {
        int[] a = new int[4];
        a[0] = 3;
        a[1] = 1;
        a[2] = 4;
        a[3] = 2;
        SortStrategy ss = new BubbleSort();
        ss.sort(a);
        assertTrue(isSorted(a));
    }
}