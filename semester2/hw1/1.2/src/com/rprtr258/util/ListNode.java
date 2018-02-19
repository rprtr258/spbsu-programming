package com.rprtr258.util;

public class ListNode {
    private ListNode next = null;
    private int value = -1;

    ListNode(int value) {
        this.next = null;
        this.value = value;
    }

    public void setNext(ListNode node) {
        next = node;
    }

    public ListNode getNext() {
        return next;
    }

    public int getValue() {
        return value;
    }
}
