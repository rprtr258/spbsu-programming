package com.rprtr258;

import org.junit.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import static org.junit.Assert.*;

public class ClassFileLoaderTest {
    @Test
    public void loadClassFileTest() throws MalformedURLException, ClassNotFoundException {
        URL classURL = getClass().getResource("AVLTree.class");
        System.out.println(classURL);
        File classFile = new File(classURL.getFile());
        System.out.println(classFile.getAbsolutePath());
        Class loadedClass = ClassFileLoader.loadClassFile(classFile);
        String[] codeLines = ClassDecompiler.getClassCode(loadedClass).split("\n");
        String[] expected = {
            "class AVLTree<T> extends Object implements Collection {",
            "    //Fields",
            "    private NodeWrapper<E> root;",
            "    private int size;",
            "    //Constructors",
            "    AVLTree();",
            "    //Methods",
            "    public volatile boolean add(Object);",
            "    public boolean add(Comparable<T>);",
            "    public boolean remove(Object);",
            "    public String toString();",
            "    public void clear();",
            "    public boolean contains(Object);",
            "    public boolean isEmpty();",
            "    public Iterator<E> iterator();",
            "    public int size();",
            "    public Object[] toArray();",
            "    public Object[] toArray(Object[]);",
            "    public boolean addAll(Collection<E>);",
            "    public boolean containsAll(Collection<E>);",
            "    public boolean removeAll(Collection<E>);",
            "    public boolean retainAll(Collection<E>);",
            "}"
        };
        Arrays.sort(codeLines);
        Arrays.sort(expected);
        assertArrayEquals(expected, codeLines);
    }
}