package com.rprtr258;

import org.junit.Test;

import static org.junit.Assert.*;

public class ExpressionTreeTest {
    @Test
    public void infixFormTest() {
        ExpressionTree tree = new ExpressionTree("(+ (- (* (/ 1 2) 3) 4) 5)");
        String treeString = tree.infixForm();
        assertEquals("(((1 / 2) * 3) - 4) + 5", treeString);
        tree = new ExpressionTree("(- (* (+ 2 2) 2) (+ (* 2 2) 2))");
        treeString = tree.infixForm();
        assertEquals("((2 + 2) * 2) - ((2 * 2) + 2)", treeString);
    }

    @Test
    public void creationTest() {
        ExpressionTree tree = new ExpressionTree("(+ (- (* (/ 1 2) 3) 4) 5)");
    }

    @Test
    public void toStringTest() {
        ExpressionTree tree = new ExpressionTree("(+ (- (* (/ 1 2) 3) 4) 5)");
        String treeString = tree.toString();
        assertEquals("(+ (- (* (/ 1 2) 3) 4) 5)", treeString);
        tree = new ExpressionTree("(- (* (+ 2 2) 2) (+ (* 2 2) 2))");
        treeString = tree.toString();
        assertEquals("(- (* (+ 2 2) 2) (+ (* 2 2) 2))", treeString);
    }

    @Test
    public void calcTest() {
        ExpressionTree tree = new ExpressionTree("(+ (- (* (/ 1 2) 3) 4) 5)");
        int result = tree.calc();
        assertEquals(result, 1);
        tree = new ExpressionTree("(- (* (+ 2 2) 2) (+ (* 2 2) 2))");
        result = tree.calc();
        assertEquals(result, 2);
    }
}