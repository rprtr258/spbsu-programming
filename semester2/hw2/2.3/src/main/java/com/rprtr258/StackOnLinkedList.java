package com.rprtr258;

import java.util.EmptyStackException;

public class StackOnLinkedList<T> implements Stack<T> {
    private LinkedList<T> data = null;

    public StackOnLinkedList() {
        data = new LinkedList<>();
    }

    public int size() {
        return data.size();
    }

    public void push(T value) {
        data.insert(value);
    }

    public T pop() {
        if (!isEmpty()) {
            T value = top();
            data.delete();
            return value;
        } else {
            throw new EmptyStackException();
        }
    }

    public T top() {
        if (!isEmpty())
            return data.peekEnd();
        else
            throw new EmptyStackException();
    }
}
