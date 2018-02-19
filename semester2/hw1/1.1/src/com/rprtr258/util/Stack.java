package com.rprtr258.util;

public class Stack {
    private LinkedList data = null;

    public Stack() {
        data = new LinkedList();
    }

    public int getSize() {
        return data.getSize();
    }

    public boolean isEmpty() {
        return data.isEmpty();
    }

    public void push(int value) {
        data.insertAtEnd(value);
    }

    public void pop() {
        data.deleteAtEnd();
    }

    public int peek() {
        return data.peekEnd();
    }
}
