package com.rprtr258;

import java.util.ArrayList;
import java.util.EmptyStackException;

public class StackOnArray<ElementType> implements Stack<ElementType> {
    private ArrayList<ElementType> data = null;

    public StackOnArray() {
        data = new ArrayList<>();
    }

    public void push(ElementType value) {
        data.add(value);
    }

    public void pop() {
        if (!isEmpty())
            data.remove(data.size() - 1);
        else
            throw new EmptyStackException();
    }

    public ElementType top() {
        if (!isEmpty())
            return data.get(data.size() - 1);
        else
            throw new EmptyStackException();
    }

    public boolean isEmpty() {
        return data.isEmpty();
    }
}
