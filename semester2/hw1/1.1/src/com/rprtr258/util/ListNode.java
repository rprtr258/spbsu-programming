package com.rprtr258.util;

public class ListNode {
    private ListNode prev = null;
    private ListNode next = null;
    private int value = -1;

    ListNode(int value) {
        this.prev = null;
        this.next = null;
        this.value = value;
    }

    public void print() {
        System.out.print(value);
        if (next != null) {
            System.out.print(", ");
            next.print();
        }
    }

    public void setNext(ListNode node) {
        next = node;
    }

    public ListNode getNext() {
        return next;
    }

    public void setPrev(ListNode node) {
        prev = node;
    }

    public ListNode getPrev() {
        return prev;
    }

    public int getValue() {
        return value;
    }
}
