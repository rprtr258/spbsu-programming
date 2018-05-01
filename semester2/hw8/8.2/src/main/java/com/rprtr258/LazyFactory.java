package com.rprtr258;

import java.util.function.Supplier;

/**
 * Lazy interface objects factory.
 */
public class LazyFactory {
    /**
     * Creates Lazy for one thread execution.
     * @param func supplier to evaluate.
     * @param <T> return type.
     * @return Lazy object.
     */
    public static <T> Lazy<T> createSingleThreadLazy(Supplier<T> func) {
        final boolean[] evaluated = {false};
        return new Lazy<T>() {
            private T result = null;

            @Override
            public T get() {
                if (!evaluated[0]) {
                    result = func.get();
                    evaluated[0] = true;
                }
                return result;
            }
        };
    }

    /**
     * Creates Lazy for multi thread execution.
     * @param func supplier to evaluate.
     * @param <T> return type.
     * @return Lazy object.
     */
    public static <T> Lazy<T> createMultiThreadLazy(Supplier<T> func) {
        final boolean[] evaluated = {false};
        return new Lazy<T>() {
            private T result = null;

            @Override
            public T get() {
                if (!evaluated[0]) {
                    synchronized (this) {
                        if (!evaluated[0]) {
                            result = func.get();
                            evaluated[0] = true;
                        }
                    }
                }
                return result;
            }
        };
    }
}
