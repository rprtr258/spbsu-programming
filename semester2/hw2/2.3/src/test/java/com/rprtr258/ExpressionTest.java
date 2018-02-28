package com.rprtr258;

import org.junit.Test;

import static org.junit.Assert.*;

public class ExpressionTest {
    @Test
    public void isCorrectTest() {
        assertTrue(isCorrect("+2 2"));
        assertTrue(isCorrect("*2 2"));
        assertTrue(isCorrect("-2 2"));
        assertTrue(isCorrect("/2 2"));
        assertTrue(isCorrect("+ - * / 1 2 3 4 5")); // ((1 / 2) * 3 - 4) + 5
        assertTrue(isCorrect("-2 *3/ 6+1 2")); // 2 - 3 * 6 / (1 + 2)

        assertFalse(isCorrect("%2 2"));
        assertFalse(isCorrect("/ 3 0")); // 3 / 0
        assertFalse(isCorrect("+ 2 - 1")); // NOT 2 + (-1); IT'S 2 + (1 - ?)
    }

    @Test(expected = IllegalArgumentException.class)
    public void incorrectExpressionTest() {
        Expression expr = new Expression("% 3 4");
    }

    @Test
    public void plusTest() {
        // 2 + 2
        Expression expr = new Expression("+ 2 2");
        assertEquals(4, expr.eval(new StackOnLinkedList<>()));
    }

    @Test
    public void minusTest() {
        // 2 - 3
        Expression expr = new Expression("- 2 3");
        assertEquals(-1, expr.eval(new StackOnLinkedList<>()));
    }

    @Test
    public void multiplyTest() {
        // 2 * 3
        Expression expr = new Expression("* 2 3");
        assertEquals(6, expr.eval(new StackOnLinkedList<>()));
    }

    @Test
    public void divideTest() {
        // 5 / 2
        Expression expr = new Expression("/ 5 2");
        assertEquals(2, expr.eval(new StackOnLinkedList<>()));
    }

    @Test
    public void allOpsTest() {
        // 3 * ((2 + 33) / (11 - 6))
        Expression expr = new Expression("* 3 / + 2 33 - 11 6");
        assertEquals(21, expr.eval(new StackOnLinkedList<>()));
    }

    private boolean isCorrect(String expr) {
        return Expression.isCorrect(expr, new StackOnArray<>());
    }
}