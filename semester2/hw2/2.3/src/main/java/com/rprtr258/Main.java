package com.rprtr258;

import java.util.*;

public class Main {
    private static Scanner in = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.print("Write expression in prefix form to calc, unary minus is not supported(e.g. +2 2):\n");
        Expression expr = inputExpression();
        int result = expr.eval();
        System.out.println("Result is: " + result);
    }

    private static Expression inputExpression() {
        String str = in.nextLine();
        Expression expr = new Expression(str);
        while (!expr.isCorrect()) {
            System.out.println("Incorrect operator found. Please retype:\n");
            str = in.nextLine();
            expr = new Expression(str);
        }
        return expr;
    }
}
