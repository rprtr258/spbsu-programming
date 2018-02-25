package com.rprtr258;

public class LinkedList<T> {
    private ListNode<T> tail = null;
    private int size = 0;

    public LinkedList() {
        tail = null;
        size = 0;
    }

    public boolean isEmpty() {
        return (size == 0);
    }

    public T peekEnd() {
        return tail.getValue();
    }

    public void insert(T value) {
        ListNode<T> newNode = new ListNode<>(value);
        newNode.setPrev(tail);
        tail = newNode;
        size++;
    }

    public void delete() {
        tail = tail.getPrev();
        size--;
    }
}

class ListNode<T> {
    private ListNode<T> prev = null;
    private T value = null;

    ListNode(T value) {
        this.prev = null;
        this.value = value;
    }

    public void setPrev(ListNode<T> node) {
        prev = node;
    }

    public ListNode<T> getPrev() {
        return prev;
    }

    public T getValue() {
        return value;
    }
}
