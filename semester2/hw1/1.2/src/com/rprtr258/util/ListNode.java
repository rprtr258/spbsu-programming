package com.rprtr258.util;

public class ListNode {
    private ListNode next;
    private int value;

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
