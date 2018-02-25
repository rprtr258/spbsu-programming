package com.rprtr258;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class Expression {
    private ArrayList<String> tokens;

    public Expression(String expr) {
        String exprCopy = expr;
        for (String op : new String[]{"+", "-", "*", "/"})
            exprCopy = exprCopy.replace(op, " " + op + " ");
        tokens = new ArrayList<>();
        StringTokenizer stringTokenizer = new StringTokenizer(exprCopy, " ", false);
        while (stringTokenizer.hasMoreTokens())
            tokens.add(stringTokenizer.nextToken());
    }

    public int eval() {
        Stack<String> stack = new StackOnLinkedList<>();
        for (int i = tokens.size() - 1; i >= 0; i--) {
            String token = tokens.get(i);
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

    private boolean isOperator(String token) {
        return ("+".equals(token) || "-".equals(token) || "*".equals(token) || "/".equals(token));
    }

    private int applyOperator(String op, int arg1, int arg2) {
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
