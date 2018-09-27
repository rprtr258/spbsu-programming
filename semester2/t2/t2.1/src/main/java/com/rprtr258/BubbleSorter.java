package com.rprtr258;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.util.Collections.swap;

/**
 * Sorter class which can sort given lists using given comparator.
 */
public class BubbleSorter {
    /**
     * Sorts given list.
     *
     * @param list list to sort.
     * @param cmp comparator which will be used.
     * @param <T> list element types.
     * @return sorted list copy.
     */
    public static <T> List<T> sort(List<T> list, Comparator<T> cmp) {
        ArrayList<T> result = new ArrayList<>(list);
        for (int i = 0; i < result.size(); i++) {
            for (int j = 0; j < i; j++) {
                if (cmp.compare(result.get(i), result.get(j)) < 0) {
                    swap(result, i, j);
                }
            }
        }
        return result;
    }
}
