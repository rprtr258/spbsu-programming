package com.rprtr258;

import org.junit.Test;

import java.util.*;

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
    public void addAll() {
        AVLTree<String> tree = new AVLTree<>();
        tree.addAll(Arrays.asList("b", "c", "a"));
        assertEquals("(b[1] (a[1] null null) (c[1] null null))", tree.toString());
        List<String> array = Arrays.asList("abcdefghijk".split(""));
        System.out.println(tree);
        tree.addAll(array);
        System.out.println(tree);
        Collections.reverse(array);
        assertTrue(tree.containsAll(array));
        tree.addAll(array);
        assertTrue(tree.containsAll(array));
    }

    @Test
    public void emptyAndClearTest() {
        AVLTree<String> tree = new AVLTree<>();
        assertTrue(tree.isEmpty());
        tree.add("b");
        tree.add("c");
        tree.add("a");
        assertFalse(tree.isEmpty());
        tree.clear();
        assertTrue(tree.isEmpty());
    }

    @Test
    public void removeTest() {
        AVLTree<String> tree = new AVLTree<>();
        tree.add("b");
        tree.add("b");
        tree.add("c");
        tree.add("a");
        assertTrue(tree.remove("b"));
        assertTrue(tree.contains("b"));
        assertTrue(tree.remove("b"));
        assertFalse(tree.contains("b"));
        assertFalse(tree.remove("g"));
        assertEquals("(a[1] null (c[1] null null))", tree.toString());
    }

    @Test
    public void removeAllTest() {
        AVLTree<String> tree = new AVLTree<>();
        tree.add("b");
        tree.add("c");
        tree.add("a");
        tree.removeAll(Arrays.asList("a", "c"));
        assertEquals("(b[1] null null)", tree.toString());
        List<String> array = Arrays.asList("dfhgushdfpguahrt0e7mzagt8p9oy".split(""));
        tree.addAll(array);
        tree.addAll(array);
        tree.removeAll(array);
        tree.removeAll(array);
        Collections.reverse(array);
        tree.addAll(array);
        tree.addAll(array);
        tree.removeAll(array);
        tree.removeAll(array);
    }

    @Test
    public void retainAllTest() {
        AVLTree<String> tree = new AVLTree<>();
        tree.add("b");
        tree.add("c");
        tree.add("a");
        tree.retainAll(Arrays.asList("b", "c"));
        assertEquals("(b[1] null (c[1] null null))", tree.toString());
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

    @Test
    public void contains() {
        AVLTree<String> tree = new AVLTree<>();
        tree.add("b");
        tree.add("c");
        tree.add("a");
        assertTrue(tree.contains("a"));
        assertTrue(tree.contains("b"));
        assertTrue(tree.contains("c"));
        assertFalse(tree.contains("d"));
    }

    @Test
    public void containsAll() {
        AVLTree<String> tree = new AVLTree<>();
        tree.addAll(Arrays.asList("abc".split("")));
        assertTrue(tree.containsAll(Arrays.asList("a", "c")));
        assertFalse(tree.containsAll(Arrays.asList("a", "g")));
    }

    @Test
    public void toObjectsArray() {
        AVLTree<String> tree = new AVLTree<>();
        tree.addAll(Arrays.asList("abc".split("")));
        String[] array = new String[tree.size()];
        tree.toArray(array);
        assertArrayEquals(new String[]{"a", "b", "c"}, array);
    }

    @Test
    public void toArray() {
        AVLTree<String> tree = new AVLTree<>();
        tree.addAll(Arrays.asList("acb".split("")));
        Object[] array = tree.toArray();
        assertEquals("a", array[0]);
        assertEquals("b", array[1]);
        assertEquals("c", array[2]);
        assertArrayEquals(new Object[]{"a", "b", "c"}, array);
        tree.add("b");
        array = tree.toArray();
        assertEquals("a", array[0]);
        assertEquals("b", array[1]);
        assertEquals("b", array[2]);
        assertEquals("c", array[3]);
        assertArrayEquals(new Object[]{"a", "b", "b", "c"}, array);
    }

    @Test
    public void remove() {
    }

    @Test
    public void removeAll() {
    }

    @Test
    public void retainAll() {
    }

    @Test
    public void size() {
    }

    @Test
    public void isEmpty() {
    }

    @Test
    public void clear() {
    }
}