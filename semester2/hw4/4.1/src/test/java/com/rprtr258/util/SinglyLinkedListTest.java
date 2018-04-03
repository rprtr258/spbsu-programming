package com.rprtr258.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class SinglyLinkedListTest {
    @Test(expected = EmptyListException.class)
    public void emptyListExceptionRemoveTest() {
        SinglyLinkedList<String> myList = new SinglyLinkedList<>();
        myList.removeLast();
    }

    @Test(expected = EmptyListException.class)
    public void emptyListExceptionPeekTest() {
        SinglyLinkedList<String> myList = new SinglyLinkedList<>();
        myList.peek();
    }

    @Test
    public void emptyListTest() {
        SinglyLinkedList<String> myList = new SinglyLinkedList<>();
        String listString = myList.toString();
        assertEquals("[]", listString);
        assertEquals(0, myList.getSize());
        assertTrue(myList.isEmpty());
    }

    @Test
    public void containsTest() throws ListAppendException {
        SinglyLinkedList<String> myList = new SinglyLinkedList<>();
        myList.append("I");
        myList.append("Am");
        myList.append("Useless");
        assertTrue(myList.contains("Useless"));
        assertFalse(myList.contains("Useful"));
    }

    @Test
    public void appendTest() throws ListAppendException {
        SinglyLinkedList<String> myList = new SinglyLinkedList<>();
        myList.append("I");
        myList.append("Am");
        myList.append("Useless");
        String listString = myList.toString();
        assertEquals("[Useless, Am, I]", listString);
        assertEquals(3, myList.getSize());
        assertFalse(myList.isEmpty());
    }

    @Test
    public void removeLastTest() throws ListAppendException {
        SinglyLinkedList<String> myList = new SinglyLinkedList<>();
        myList.append("I");
        myList.append("Am");
        myList.append("Useless");
        myList.append("?");
        myList.removeLast();
        assertEquals(3, myList.getSize());
        myList.removeLast();
        assertEquals(2, myList.getSize());
        myList.removeLast();
        assertEquals(1, myList.getSize());
        myList.removeLast();
        assertEquals(0, myList.getSize());
        assertTrue(myList.isEmpty());
    }

    @Test
    public void peekTest() throws ListAppendException {
        SinglyLinkedList<String> myList = new SinglyLinkedList<>();
        myList.append("I");
        assertEquals("I", myList.peek());
        myList.append("Am");
        assertEquals("Am", myList.peek());
        myList.append("Useless");
        assertEquals("Useless", myList.peek());
    }
}