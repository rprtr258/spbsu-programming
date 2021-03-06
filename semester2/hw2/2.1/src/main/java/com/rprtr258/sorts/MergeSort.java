package com.rprtr258.sorts;

public class MergeSort implements SortStrategy {

    private void merge(int[] result, int[] first, int[] second) {
        int i = 0;
        int j = 0;
        int k = 0;
        while (i < first.length && j < second.length) {
            if (first[i] < second[j]) {
                result[k] = first[i];
                i++;
            } else {
                result[k] = second[j];
                j++;
            }
            k++;
        }
        if (i < first.length)
            System.arraycopy(first, i, result, k, first.length - i);
        else if (j < second.length)
            System.arraycopy(second, j, result, k, second.length - j);
    }

    public void sort(int[] array) {
        if (array.length <= 1)
            return;

        int mid = array.length / 2;
        int[] leftPart = new int[mid];
        System.arraycopy(array, 0, leftPart, 0, mid);

        int[] rightPart = new int[array.length - mid];
        System.arraycopy(array, mid, rightPart, 0, array.length - mid);

        sort(leftPart);
        sort(rightPart);

        merge(array, leftPart, rightPart);
    }
}
