package com.rprtr258.hashtable;

import org.junit.Test;

import static org.junit.Assert.*;

public class CharSumHashTest {
    @Test
    public void calcTest() {
        HashStrategy hashStrategy = new CharSumHash(999);
        assertEquals(237, hashStrategy.hash("LUL"));
        assertEquals(0, hashStrategy.hash(""));
    }

    @Test
    public void maxHashTest() {
        HashStrategy hashStrategy = new CharSumHash(999);
        assertEquals(998, hashStrategy.maxHashValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void illegalParamsTest() {
        HashStrategy hashStrategy = new CharSumHash(-1);
    }
}