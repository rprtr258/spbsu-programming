package com.rprtr258;

import org.junit.Test;

import java.util.function.Supplier;

import static org.junit.Assert.*;

public class LazyFactoryTest {
    @Test
    public void singleThreadLazyOneCallTest() {
        Integer expected = createSingleCallFunc().get();
        Lazy<Integer> lazy = LazyFactory.createSingleThreadLazy(createSingleCallFunc());
        for (int i = 0; i < 1000; i++)
            assertEquals(expected, lazy.get());
    }

    @Test
    public void multiThreadLazyOneCallTest() {
        Integer expected = createSingleCallFunc().get();
        Lazy<Integer> lazy = LazyFactory.createMultiThreadLazy(createSingleCallFunc());
        final int THREADS = 1000;
        Thread threads[] = new Thread[THREADS];
        Runnable runnable = () -> assertEquals(expected, lazy.get());
        for (int i = 0; i < THREADS; i++)
            threads[i] = new Thread(runnable);
        for (int i = 0; i < THREADS; i++)
            threads[i].start();
    }

    @Test
    public void singleThreadLazyHeavyCalcTest() {
        Integer expected = createHeavyCalcFunc().get();
        Lazy<Integer> lazy = LazyFactory.createSingleThreadLazy(createHeavyCalcFunc());
        for (int i = 0; i < 1000; i++)
            assertEquals(expected, lazy.get());
    }

    @Test
    public void multiThreadLazyHeavyCalcTest() {
        Integer expected = createHeavyCalcFunc().get();
        Lazy<Integer> lazy = LazyFactory.createMultiThreadLazy(createHeavyCalcFunc());
        final int THREADS = 1000;
        Thread threads[] = new Thread[THREADS];
        Runnable runnable = () -> assertEquals(expected, lazy.get());
        for (int i = 0; i < THREADS; i++)
            threads[i] = new Thread(runnable);
        for (int i = 0; i < THREADS; i++)
            threads[i].start();
    }

    private Supplier<Integer> createHeavyCalcFunc() {
        final boolean[] wasntCalled = {true};
        return () -> {
            assertTrue(wasntCalled[0]);
            wasntCalled[0] = false;
            int result = 2;
            for (int i = 1; i < 12345; i++) {
                result = (result * i - i) % 1000000007;
            }
            return result;
        };
    }

    private Supplier<Integer> createSingleCallFunc() {
        final boolean[] wasntCalled = {true};
        return () -> {
            assertTrue(wasntCalled[0]);
            wasntCalled[0] = false;
            return 42;
        };
    }
}