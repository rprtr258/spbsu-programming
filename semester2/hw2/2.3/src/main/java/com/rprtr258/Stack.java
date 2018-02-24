package com.rprtr258;

public interface Stack<ElementType> {
    void push(ElementType value);
    void pop();
    ElementType top();
    boolean isEmpty();
}
