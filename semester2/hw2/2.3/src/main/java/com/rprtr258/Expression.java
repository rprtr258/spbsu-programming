package com.rprtr258;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;

public class Expression {
    private ArrayList<String> tokens = new ArrayList<>();
    private static String[] operands = new String[]{"+", "-", "*", "/"};

    public Expression(String expr) {
        if (!isCorrect(expr, new StackOnArray<>()))
            throw new IllegalArgumentException();
        String exprCopy = expr;
        for (String op : operands)
            exprCopy = exprCopy.replace(op, " " + op + " ");
        StringTokenizer stringTokenizer = new StringTokenizer(exprCopy, " ", false);
        while (stringTokenizer.hasMoreTokens())
            tokens.add(stringTokenizer.nextToken());
        Collections.reverse(tokens);
    }

    public int eval(Stack<String> stack) {
        for (String token : tokens) {
            if (isOperator(token)) {
                int arg1 = Integer.parseInt(stack.pop());
                int arg2 = Integer.parseInt(stack.pop());
                int res = applyOperator(token, arg1, arg2);
                stack.push(Integer.toString(res));
            } else {
                stack.push(token);
            }
        }
        return Integer.parseInt(stack.top());
    }

    public static boolean isCorrect(String expr, Stack<String> stack) {
        String exprCopy = expr;
        for (String op : operands)
            exprCopy = exprCopy.replace(op, " " + op + " ");
        StringTokenizer stringTokenizer = new StringTokenizer(exprCopy, " ", false);
        ArrayList<String> tokens = new ArrayList<>();
        while (stringTokenizer.hasMoreTokens())
            tokens.add(stringTokenizer.nextToken());
        Collections.reverse(tokens);
        for (String token : tokens) {
            if (isOperator(token)) {
                if (stack.size() < 2 || !isNumber(stack.top()))
                    return false;
                int arg1 = Integer.parseInt(stack.pop());
                if (!isNumber(stack.top()))
                    return false;
                int arg2 = Integer.parseInt(stack.pop());
                if (arg2 == 0 && "/".equals(token))
                    return false;
                int res = applyOperator(token, arg1, arg2);
                stack.push(Integer.toString(res));
            } else if (isNumber(token)) {
                stack.push(token);
            } else {
                return false;
            }
        }
        return (stack.size() == 1);
    }

    private static boolean isOperator(String token) {
        return Arrays.asList(operands).contains(token);
    }

    private static boolean isNumber(String token) {
        for (int i = 1; i < token.length(); i++) {
            if (!Character.isDigit(token.charAt(i)))
                return false;
        }
        return (token.charAt(0) == '-' || Character.isDigit(token.charAt(0)));
    }

    private static int applyOperator(String op, int arg1, int arg2) {
        if ("+".equals(op))
            return arg1 + arg2;
        if ("-".equals(op))
            return arg1 - arg2;
        if ("*".equals(op))
            return arg1 * arg2;
        if ("/".equals(op))
            return arg1 / arg2;
        throw new IllegalArgumentException();
    }
}
