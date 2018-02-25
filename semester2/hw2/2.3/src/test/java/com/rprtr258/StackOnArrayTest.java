package com.rprtr258;

import org.junit.Test;

import java.util.EmptyStackException;

import static org.junit.Assert.*;

public class StackOnArrayTest {
    @Test
    public void pushTest() {
        Stack<Integer> stack = new StackOnArray<>();
        for (int i = 0; i < 10; i++)
            stack.push(i);
    }

    @Test
    public void topTest() {
        Stack<Integer> stack = new StackOnArray<>();
        for (int i = 0; i < 10; i++) {
            stack.push(i);
            assertEquals(stack.top(), new Integer(i));
        }
    }

    @Test
    public void popTest() {
        Stack<Integer> stack = new StackOnArray<>();
        for (int i = 0; i < 10; i++) {
            stack.push(i);
            stack.pop();
        }
        for (int i = 0; i < 10; i++)
            stack.push(i);
        for (int i = 0; i < 10; i++)
            stack.pop();
    }

    @Test(expected = EmptyStackException.class)
    public void emptyPopTest() {
        Stack<Integer> stack = new StackOnArray<>();
        stack.push(1);
        stack.pop();
        stack.pop();
    }

    @Test
    public void isEmptyTest() {
        Stack<Integer> stack = new StackOnArray<>();
        for (int i = 0; i < 10; i++) {
            stack.push(i);
            stack.pop();
            assertTrue(stack.isEmpty());
        }
        for (int i = 0; i < 10; i++) {
            stack.push(i);
            assertFalse(stack.isEmpty());
        }
        for (int i = 0; i < 10; i++) {
            stack.pop();
            assertFalse(i < 9 && stack.isEmpty());
        }
        assertTrue(stack.isEmpty());
    }

    @Test
    public void elementCopyTest() {
        Stack<Integer> stack = new StackOnLinkedList<>();
        Integer sample = 10;
        stack.push(sample);
        sample = 3;
        assertEquals(new Integer(10), stack.top());
    }
}