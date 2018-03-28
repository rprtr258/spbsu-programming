package com.rprtr258;

import org.junit.Test;

import static org.junit.Assert.*;

public class ExpressionTest {
    @Test
    public void checkCorrectnessTest() {
        assertTrue(Expression.checkCorrectness(""));
        assertTrue(Expression.checkCorrectness("-2+2/7-8*452.5"));
        assertTrue(Expression.checkCorrectness("2+2/7-8*452.5"));
        assertFalse(Expression.checkCorrectness("ajghrenlgiu"));
        assertFalse(Expression.checkCorrectness("02+2/7-8*452.5")); // 0 in beginning of number
        assertFalse(Expression.checkCorrectness("2+*452.5")); // more than 1 operator
        assertFalse(Expression.checkCorrectness("2+452,5")); // comma instead of point
        assertFalse(Expression.checkCorrectness("2+4.52.5")); // more than 1 point
    }

    @Test
    public void evaluateTest() throws InvalidExpressionException {
        assertEquals(0, Expression.evaluate(""), 1e-6);
        assertEquals(-3617.714285, Expression.evaluate("2+2/7-8*452.5"), 1e-6);
        assertEquals(-3621.714285, Expression.evaluate("-2+2/7-8*452.5"), 1e-6);
    }

    @Test(expected = InvalidExpressionException.class)
    public void invalidExpressionExceptionTest() throws InvalidExpressionException {
        assertEquals(0, Expression.evaluate("100500/.11"), 1e-6);
    }
}