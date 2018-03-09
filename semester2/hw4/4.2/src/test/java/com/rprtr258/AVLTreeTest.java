package com.rprtr258;

import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

public class AVLTreeTest {
    @Test
    public void addTest() {
        AVLTree<String> tree = new AVLTree<>();
        tree.add("b");
        assertEquals("(b[1] null null)", tree.toString());
        tree.add("c");
        assertEquals("(b[1] null (c[1] null null))", tree.toString());
        tree.add("a");
        assertEquals("(b[1] (a[1] null null) (c[1] null null))", tree.toString());
    }

    @Test
    public void removeTest() {
        AVLTree<String> tree = new AVLTree<>();
        tree.add("b");
        tree.add("c");
        tree.add("a");
        tree.remove("b");
        assertEquals("(a[1] null (c[1] null null))", tree.toString());
    }

    @Test
    public void iteratorTest() {
        AVLTree<String> tree = new AVLTree<>();
        tree.add("b");
        tree.add("c");
        tree.add("a");
        Iterator<String> iter = tree.iterator();
        StringBuilder travel = new StringBuilder();
        while (iter.hasNext())
            travel.append(iter.next()).append(" ");
        assertEquals("a b c ", travel.toString());
    }
}