package com.rprtr258;

public class Main {
    public static void main(String[] args) {
        int[] a = new int[4];
        a[0] = 3;
        a[1] = 1;
        a[2] = 4;
        a[3] = 2;
        for (int i = 0; i < a.length; i++)
            System.out.print(a[i]);
        System.out.print("\n");
        SortStrategy ss = new BubbleSort();
        ss.sort(a);
        for (int i = 0; i < a.length; i++)
            System.out.print(a[i]);
        System.out.print("\n");
    }
}
