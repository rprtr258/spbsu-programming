package com.rprtr258;

import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class Expression {
    private ArrayList<String> tokens = new ArrayList<>();
    private boolean evaluated = false;
    private int result = 0;

    public Expression(String expr) {
        String exprCopy = expr;
        for (String op : new String[]{"+", "-", "*", "/"})
            exprCopy = exprCopy.replace(op, " " + op + " ");
        StringTokenizer stringTokenizer = new StringTokenizer(exprCopy, " ", false);
        while (stringTokenizer.hasMoreTokens())
            tokens.add(stringTokenizer.nextToken());
        Collections.reverse(tokens);
    }

    public int eval() {
        if (!evaluated) {
            Stack<String> stack = new StackOnLinkedList<>();
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
            result = Integer.parseInt(stack.top());
            evaluated = true;
        }
        return result;
    }

    public boolean isCorrect() {
        try {
            eval();
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
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
