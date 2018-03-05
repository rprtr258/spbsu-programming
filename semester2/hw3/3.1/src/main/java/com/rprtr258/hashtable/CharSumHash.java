package com.rprtr258.hashtable;

public class CharSumHash implements HashStrategy {
    private int bound;

    public CharSumHash() {
        this(10000);
    }

    public CharSumHash(int bound) {
        if (bound <= 0)
            throw new IllegalArgumentException();
        this.bound = bound;
    }

    @Override
    public int hash(String string) {
        int result = 0;
        for (int i = 0; i < string.length(); i++)
            result += string.charAt(i);
        return result % bound;
    }

    @Override
    public int maxHashValue() {
        return bound - 1;
    }
}
