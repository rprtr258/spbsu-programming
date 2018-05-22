package com.rprtr258;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class BubbleSorterTest {
    @Test
    public void intSortTest() {
        testType(Arrays.asList(4, 1, 3, 2), Integer::compare);
    }

    @Test
    public void stringSortTest() {
        testType(Arrays.asList("", "abc", "fasdf", "235(&*%^", "❦ ❧ ☙ ❥ ❣ ♡ ♥ ❤ ➳ ღ💟💘💝🎔❦💓💔💕💖💗💙💚💛💜💞🖤🧡♡♥❤💑❣❧☙❥🂱🂲🂳🂴🂵🂶🂷🂸🂹🂺🂻🂼🂽🂾😍😻⼼⺖⺗心忄痵怆愴肓衷懑"), (s1, s2) -> (s1.compareTo(s2)));
    }

    @Test
    public void myVectorSortTest() {
        List<Vector> list = Arrays.asList(new Vector(), new Vector(new int[]{1, 2, 3}), new Vector(new int[]{1, 2}));
        testType(list, this::compareVectors);
    }

    private <T> void testType(List<T> list, Comparator<T> cmp) {
        List<T> expected = new ArrayList<>(list);
        expected.sort(cmp);
        assertEquals(expected, BubbleSorter.sort(list, cmp));
    }

    private int compareVectors(Vector v1, Vector v2) {
        if (v1.data.length != v2.data.length)
            return v1.data.length - v2.data.length;
        for (int i = 0; i < v1.data.length; i++)
            if (v1.data[i] != v2.data[i])
                return v1.data[i] - v2.data[i];
        return 0;
    }

    private class Vector {
        int[] data = null;

        private Vector() {
            data = new int[]{};
        }

        private Vector(int[] array) {
            data = new int[array.length];
            System.arraycopy(array, 0, data, 0, array.length);
        }
    }
}