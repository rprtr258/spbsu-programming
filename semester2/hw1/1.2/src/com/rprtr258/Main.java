package com.rprtr258;

import com.rprtr258.util.LinkedList;

public class Main {
    private static void testInsert(LinkedList list, int value) {
        list.insert(value);
        System.out.printf("Inserted %d in list\n", value);
    }

    private static void testRemove(LinkedList list) {
        if (list.isEmpty()) {
            System.out.print("There is no elements to remove!\n");
        } else {
            int value = list.peek();
            list.removeElement();
            System.out.printf("Removed %d in list\n", value);
        }
    }

    public static void main(String[] args) {
        LinkedList list = new LinkedList();
        testInsert(list, 10);
        testInsert(list, 20);
        testRemove(list);
        testInsert(list, 30);
        testRemove(list);
        testRemove(list);
        testRemove(list);
    }
}
