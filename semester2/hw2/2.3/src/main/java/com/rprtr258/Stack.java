package com.rprtr258;

public interface Stack<T> {
    void push(T value);
    void pop();
    T top();
    boolean isEmpty();
}
