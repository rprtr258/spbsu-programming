package com.rprtr258.hashtable;

import org.junit.Test;

import static org.junit.Assert.*;

public class PolynomialHashTest {
    @Test
    public void calcTest() {
        HashStrategy hashStrategy = new PolynomialHash(13, 89);
        assertEquals(52, hashStrategy.hash("LUL"));
        assertEquals(0, hashStrategy.hash(""));
    }

    @Test
    public void maxHashTest() {
        HashStrategy hashStrategy = new PolynomialHash(13, 89);
        assertEquals(88, hashStrategy.maxHashValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void illegalParamsTest() {
        HashStrategy hashStrategy = new PolynomialHash(-1, 89);
    }
}