package com.rprtr258.client;

/**
 * Object to hold double value.
 */
public class DoubleProperty {
    private double value = 0.0;

    /**
     * Value setter.
     * @param newValue new value to store
     */
    public void setValue(double newValue) {
        value = newValue;
    }

    /**
     * Value getter.
     * @return stored value
     */
    public double getValue() {
        return value;
    }
}
