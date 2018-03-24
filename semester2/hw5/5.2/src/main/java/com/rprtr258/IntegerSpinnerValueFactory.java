package com.rprtr258;

import javafx.scene.control.SpinnerValueFactory;

/**
 * Value factory needed by JavaFX Spinner. Holds Integer, increments and decrements it as usual.
 */
public class IntegerSpinnerValueFactory extends SpinnerValueFactory<Integer> {
    /**
     * Decrements held value <b>steps</b> times.
     * @param steps times to decrement by 1.
     */
    @Override
    public void decrement(int steps) {
        setValue(getValue() - steps);
    }

    /**
     * Increments held value <b>steps</b> times.
     * @param steps times to increment by 1.
     */
    @Override
    public void increment(int steps) {
        setValue(getValue() + steps);
    }
}
