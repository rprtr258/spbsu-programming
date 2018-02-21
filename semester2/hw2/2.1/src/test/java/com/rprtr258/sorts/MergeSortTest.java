package com.rprtr258.sorts;

import org.junit.Test;

import static org.junit.Assert.*;

public class MergeSortTest {
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
        int[] a = {3, 1, 4, 2};
        SortStrategy ss = new MergeSort();
        ss.sort(a);
        assertTrue(isSorted(a));
    }
}