package com.rprtr258;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ParallelQSorter {
    public static void sort(int[] a) {
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(new SortAction(a, 0, a.length - 1));
    }

    private static class SortAction extends RecursiveAction {
        private int[] a = null;
        private int i = -1;
        private int j = -1;

        private SortAction(int[] a, int i, int j) {
            this.a = a;
            this.i = i;
            this.j = j;
        }

        @Override
        protected void compute() {
            if (i >= j)
                return;
            int k = partition();
            RecursiveAction leftPart = new SortAction(a, i, k - 1);
            RecursiveAction rightPart = new SortAction(a, k, j);
            leftPart.fork();
            rightPart.fork();
            leftPart.join();
            rightPart.join();
        }

        private int partition() {
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
}
