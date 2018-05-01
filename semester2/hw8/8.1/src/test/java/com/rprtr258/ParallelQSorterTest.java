package com.rprtr258;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class ParallelQSorterTest {
    @Test
    public void testSort() {
        for (int i = 0; i < 100; i++)
            test(i);
    }

    private void test(int size) {
        int[] array = new int[size];
        Random generator = new Random();
        for (int i = 0; i < array.length; i++)
            array[i] = generator.nextInt();
        ParallelQSorter.sort(array);
        assertTrue(isSorted(array));
    }

    private boolean isSorted(int[] a) {
        for (int i = 0; i + 1 < a.length; i++)
            if (a[i] > a[i + 1])
                return false;
        return true;
    }
}