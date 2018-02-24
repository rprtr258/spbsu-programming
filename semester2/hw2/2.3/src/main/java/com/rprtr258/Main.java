package com.rprtr258;

import java.util.*;

public class Main {
    private static Scanner in = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.print("Write expression in prefix form to calc(e.g. +2 2)\n");
        String expr = in.nextLine();
        ArrayList<String> tokens = parseTokens(expr);
        Stack<String> stack = new StackOnLinkedList();
        for (int i = tokens.size() - 1; i >= 0; i--) {
            String token = tokens.get(i);
            if ("+".equals(token)) {
                String arg1 = stack.pop();
                String arg2 = stack.pop();
                int res = Integer.parseInt(arg1) + Integer.parseInt(arg2);
                stack.push(Integer.toString(res));
            } else if ("-".equals(token)) {
                String arg1 = stack.pop();
                String arg2 = stack.pop();
                int res = Integer.parseInt(arg1) - Integer.parseInt(arg2);
                stack.push(Integer.toString(res));
            } else if ("*".equals(token)) {
                String arg1 = stack.pop();
                String arg2 = stack.pop();
                int res = Integer.parseInt(arg1) * Integer.parseInt(arg2);
                stack.push(Integer.toString(res));
            } else if ("/".equals(token)) {
                String arg1 = stack.pop();
                String arg2 = stack.pop();
                int res = Integer.parseInt(arg1) / Integer.parseInt(arg2);
                stack.push(Integer.toString(res));
            } else {
                stack.push(token);
            }
        }
        System.out.println("Result is: " + stack.top());
    }
    
    private static ArrayList<String> parseTokens(String expr) {
        ArrayList<String> result = new ArrayList();
        String token = "";
        for (int i = 0; i < expr.length(); i++) {
            char symbol = expr.charAt(i);
            if (symbol == '+' || symbol == '-' || symbol == '*' || symbol == '/') {
                if (!"".equals(token)) {
                    result.add(token);
                    token = "";
                }
                result.add("" + symbol);
            } else if (symbol == ' ') {
                if (!"".equals(token)) {
                    result.add(token);
                    token = "";
                }
            } else {
                token += symbol;
            }
        }
        if (!"".equals(token))
            result.add(token);
        
        return result;
    }
}
