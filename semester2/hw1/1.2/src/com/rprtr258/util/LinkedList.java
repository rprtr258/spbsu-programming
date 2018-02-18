package com.rprtr258.util;

public class LinkedList {
    private ListNode head = null;
    private int size = 0;

    public LinkedList() {
        head = null;
        size = 0;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return (size == 0);
    }

    public int peek() {
        if (size == 0)
            return 999999;
        else
            return head.getValue();
    }

    public void insert(int value) {
        if (size == 0) {
            head = new ListNode(value);
        } else {
            ListNode newNode = new ListNode(value);
            newNode.setNext(head);
            head = newNode;
        }
        size++;
    }

    public void removeElement() {
        if (size == 0) {
            System.out.print("There is no elements to remove!\n");
            return;
        }

        if (size == 1) {
            head = null;
        } else {
            head = head.getNext();
        }
        size--;
    }

    public void print() {
        ListNode tmp = head;
        System.out.print("[");
        while (tmp != null) {
            System.out.print(tmp.getValue());
            if (tmp.getNext() != null)
                System.out.print(", ");
            tmp = tmp.getNext();
        }
        System.out.print("]\n");
    }
}
