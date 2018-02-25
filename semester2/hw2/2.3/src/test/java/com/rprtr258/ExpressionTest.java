package com.rprtr258;

import org.junit.Test;

import static org.junit.Assert.*;

public class ExpressionTest {
    @Test
    public void plusTest() {
        // 2 + 2
        Expression expr = new Expression("+ 2 2");
        assertEquals(4, expr.eval());
    }

    @Test
    public void minusTest() {
        // 2 - 3
        Expression expr = new Expression("- 2 3");
        assertEquals(-1, expr.eval());
    }

    @Test
    public void multiplyTest() {
        // 2 * 3
        Expression expr = new Expression("* 2 3");
        assertEquals(6, expr.eval());
    }

    @Test
    public void divideTest() {
        // 5 / 2
        Expression expr = new Expression("/ 5 2");
        assertEquals(2, expr.eval());
    }

    @Test
    public void allOpsTest() {
        // 3 * ((2 + 33) / (11 - 6))
        Expression expr = new Expression("* 3 / + 2 33 - 11 6");
        assertEquals(21, expr.eval());
    }
}