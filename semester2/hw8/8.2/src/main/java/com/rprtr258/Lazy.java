package com.rprtr258;

/**
 * Lazy evaluation of function wrapper.
 * @param <T> result type.
 */
public interface Lazy<T> {
    /**
     * Returns result of evaluation.
     * @return result of evaluation.
     */
    T get();
}
