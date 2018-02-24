package com.rprtr258;

import java.util.EmptyStackException;

public class StackOnLinkedList<ElementType> implements Stack<ElementType> {
    private LinkedList<ElementType> data = null;

    public StackOnLinkedList() {
        data = new LinkedList<>();
    }

    public boolean isEmpty() {
        return data.isEmpty();
    }

    public void push(ElementType value) {
        data.insert(value);
    }

    public void pop() {
        if (!isEmpty())
            data.delete();
        else
            throw new EmptyStackException();
    }

    public ElementType top() {
        if (!isEmpty())
            return data.peekEnd();
        else
            throw new EmptyStackException();
    }
}
