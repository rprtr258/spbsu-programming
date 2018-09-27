package com.rprtr258;

public class QSorter {
    public static void sort(int[] a) {
        sort(a, 0, a.length - 1);
    }

    private static void sort(int[] a, int i, int j) {
        if (i >= j)
            return;
        int k = partition(a, i, j);
        sort(a, i, k - 1);
        sort(a, k, j);
    }

    private static int partition(int[] a, int i, int j) {
        int mid = a[(i + j) / 2];
        int l = i;
        int r = j;
        while (l <= r) {
            while (a[l] < mid)
                l++;
            while (a[r] > mid)
                r--;
            if (l <= r) {
                swap(a, l, r);
                l++;
                r--;
            }
        }
        return l;
    }

    private static void swap(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }
}
