package com.rprtr258;

import com.rprtr258.util.Stack;

public class Main {
    private static void testPush(Stack stack, int value) {
        stack.push(value);
        System.out.printf("Pushed %d to the stack\n", value);
    }

    private static void testPop(Stack stack) {
        int value = stack.peek();
        stack.pop();
        System.out.printf("Popped %d from the stack\n", value);
    }

    public static void main(String[] args) {
        Stack stack = new Stack();
        testPush(stack, 1);
        testPush(stack, 2);
        testPop(stack);
        testPush(stack, 3);
        testPop(stack);
        testPop(stack);
        testPop(stack);
    }
}
