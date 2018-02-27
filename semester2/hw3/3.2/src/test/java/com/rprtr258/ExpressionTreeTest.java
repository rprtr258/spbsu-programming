package com.rprtr258;

import org.junit.Test;

import static org.junit.Assert.*;

public class ExpressionTreeTest {
    @Test
    public void creationTest() {
        ExpressionTree tree = new ExpressionTree("(+ (- (* (/ 1 2) 3) 4) 5)");
    }

    @Test
    public void toStringTest() {
        ExpressionTree tree = new ExpressionTree("(+ (- (* (/ 1 2) 3) 4) 5)");
        String treeString = tree.toString();
        assertEquals(treeString, "(+ (- (* (/ 1 2) 3) 4) 5)");
    }

    @Test
    public void calcTest() {
        ExpressionTree tree = new ExpressionTree("(+ (- (* (/ 1 2) 3) 4) 5)");
        int result = tree.calc();
        assertEquals(result, 1);
    }
}