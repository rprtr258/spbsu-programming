package com.rprtr258.hashtable;

import org.junit.Test;

import static org.junit.Assert.*;

public class HashTableTest {
    @Test
    public void insertTest() {
        HashTable hashTable = new HashTable(new CharSumHash());
        hashTable.insert("lol");
        hashTable.insert("lol");
        hashTable.insert("lol");
        hashTable.insert("kek");
        hashTable.insert("cheburek");
        assertEquals(3, hashTable.getElementsCount());
    }

    @Test
    public void removeTest() {
        HashTable hashTable = new HashTable(new CharSumHash());
        hashTable.insert("lol");
        hashTable.insert("kek");
        hashTable.insert("cheburek");
        hashTable.insert("cheburek");
        hashTable.remove("lol");
        hashTable.remove("cheburek");
        hashTable.remove("cheburek");
        hashTable.remove("cheburek");
        assertEquals(1, hashTable.getElementsCount());
    }

    @Test
    public void eraseTest() {
        HashTable hashTable = new HashTable(new CharSumHash());
        hashTable.insert("lol");
        hashTable.insert("kek");
        hashTable.insert("cheburek");
        hashTable.insert("cheburek");
        hashTable.erase();
        assertEquals(0, hashTable.getElementsCount());
    }

    @Test
    public void statisticsTest() {
        HashTable hashTable = new HashTable(new CharSumHash());
        for (int i = 0; i < 30000; i++)
            hashTable.insert(Integer.toString(i));
        String expected = "Cells: 30000\n" +
            "Load factor: 3,000000\n" +
            "Conflicts: 111\n" +
            "Max chain length: 1330";
        assertEquals(expected, hashTable.getStatisticsAsString());
    }

    @Test
    public void toStringTest() {
        HashTable hashTable = new HashTable(new CharSumHash());
        hashTable.insert("Kappa");
        hashTable.insert("Keepo");
        hashTable.insert("KappaRoss");
        hashTable.insert("KappaClaus");
        hashTable.insert("Kappa");
        String expected = "Hashtable of size 10000:\n" +
            "493: Kappa\n" +
            "500: Keepo\n" +
            "916: KappaRoss\n" +
            "997: KappaClaus\n";
        assertEquals(expected, hashTable.toString());
    }

    @Test
    public void changeHashTest() {
        HashTable hashTable = new HashTable(new CharSumHash());
        for (int i = 0; i < 30000; i++)
            hashTable.insert(Integer.toString(i));
        hashTable.setHashStrategy(new CharSumHash(10));
        hashTable.setHashStrategy(new CharSumHash(1));
        hashTable.setHashStrategy(new CharSumHash(23662));
    }

    // TODO: move in CharSumHash tests
    @Test(expected = IllegalArgumentException.class)
    public void incorrectHashTest() {
        HashTable hashTable = new HashTable(new CharSumHash());
        for (int i = 0; i < 30000; i++)
            hashTable.insert(Integer.toString(i));
        hashTable.setHashStrategy(new CharSumHash(-1));
    }
}