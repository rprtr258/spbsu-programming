package com.rprtr258.sorts;

import org.junit.Test;

import static org.junit.Assert.*;

public class BubbleSortTest extends SortStrategyTest {
    private static SortStrategy sorter = new BubbleSort();

    @Test
    public void customArrayTest() {
        boolean result = sortTest(new int[]{3, 1, 4, 2}, sorter);
        assertTrue(result);
    }

    @Test
    public void emptyArrayTest() {
        boolean result = sortTest(new int[]{}, sorter);
        assertTrue(result);
    }

    @Test
    public void sortedTest() {
        boolean result = sortTest(new int[]{1, 2, 3, 4, 5, 6}, sorter);
        assertTrue(result);
    }

    @Test
    public void reversedTest() {
        boolean result = sortTest(new int[]{6, 5, 4, 3, 2, 1}, sorter);
        assertTrue(result);
    }

    @Test
    public void randomTest() {
        boolean result = randomTest(sorter);
        assertTrue(result);
    }
}