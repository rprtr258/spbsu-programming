package com.rprtr258;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Write expression in prefix form to calc, unary minus is not supported(e.g. +2 2):\n");
        Expression expr = inputExpression(in);
        int result = expr.eval(new StackOnLinkedList<>());
        System.out.printf("Result is: %d\n", result);
    }

    private static Expression inputExpression(Scanner in) {
        String str = in.nextLine();
        while (!Expression.isCorrect(str, new StackOnArray<>())) {
            System.out.print("Incorrect expression. Please retype:\n");
            str = in.nextLine();
        }
        return new Expression(str);
    }
}
