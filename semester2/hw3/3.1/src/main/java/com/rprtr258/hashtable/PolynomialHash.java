package com.rprtr258.hashtable;

public class PolynomialHash implements HashStrategy {
    private int p = -1;
    private int mod = -1;

    public PolynomialHash(int param, int modulo) {
        if (modulo < 1 || param < 1)
            throw new IllegalArgumentException();
        p = param;
        mod = modulo;
    }

    @Override
    public int hash(String string) {
        int result = 0;
        for (int i = 0; i < string.length(); i++)
            result = (result * p + string.charAt(i)) % mod;
        return result;
    }

    @Override
    public int maxHashValue() {
        return mod - 1;
    }
}
