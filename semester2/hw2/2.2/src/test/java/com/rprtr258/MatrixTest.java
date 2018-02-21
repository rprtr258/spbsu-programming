package com.rprtr258;

import org.junit.Test;

import static org.junit.Assert.*;

public class MatrixTest {
    @Test
    public void printTest() {
        int[][] sampleMatrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        Matrix matrix = new Matrix(sampleMatrix);
        matrix.writeTo(new ConsoleWriter());
    }
}