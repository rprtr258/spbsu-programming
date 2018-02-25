package com.rprtr258;

import java.util.ArrayList;
import java.util.EmptyStackException;

public class StackOnArray<T> implements Stack<T> {
    private ArrayList<T> data = null;

    public StackOnArray() {
        data = new ArrayList<>();
    }

    public void push(T value) {
        data.add(value);
    }

    public T pop() {
        if (!isEmpty()) {
            T value = top();
            data.remove(data.size() - 1);
            return value;
        } else {
            throw new EmptyStackException();
        }
    }

    public T top() {
        if (!isEmpty())
            return data.get(data.size() - 1);
        else
            throw new EmptyStackException();
    }

    public boolean isEmpty() {
        return data.isEmpty();
    }
}
