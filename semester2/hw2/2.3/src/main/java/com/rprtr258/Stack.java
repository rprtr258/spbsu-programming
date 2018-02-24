package com.rprtr258;

public interface Stack<T> {
    void push(T value);
    T pop();
    T top();
    boolean isEmpty();
}
