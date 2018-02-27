package com.rprtr258;

public class ReferenceInt {
    private int value = 0;

    public ReferenceInt(int value) {
        this.value = value;
    }

    public void set(int value) {
        this.value = value;
    }

    public int get() {
        return value;
    }

    public void inc() {
        value++;
    }
}
