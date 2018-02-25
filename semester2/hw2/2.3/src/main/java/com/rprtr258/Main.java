package com.rprtr258;

import java.util.*;

public class Main {
    private static Scanner in = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.print("Write expression in prefix form to calc, unary minus is not supported(e.g. +2 2):\n");
        String str = in.nextLine();
        Expression expr = new Expression(str);
        int result = expr.eval();
        System.out.println("Result is: " + result);
    }
}
