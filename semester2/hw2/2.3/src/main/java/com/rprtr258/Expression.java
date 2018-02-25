package com.rprtr258;

import java.util.ArrayList;

public class Expression {
    private ArrayList<String> tokens;

    public Expression(String expr) {
        tokens = new ArrayList<>();
        String token = "";
        for (int i = 0; i < expr.length(); i++) {
            char symbol = expr.charAt(i);
            if (isOperator(symbol)) {
                if (!"".equals(token)) {
                    tokens.add(token);
                    token = "";
                }
                tokens.add("" + symbol);
            } else if (symbol == ' ') {
                if (!"".equals(token)) {
                    tokens.add(token);
                    token = "";
                }
            } else {
                token += symbol;
            }
        }
        if (!"".equals(token))
            tokens.add(token);
    }

    public int eval() {
        Stack<String> stack = new StackOnLinkedList<>();
        for (int i = tokens.size() - 1; i >= 0; i--) {
            String token = tokens.get(i);
            if (isOperator(token)) {
                int arg1 = Integer.parseInt(stack.pop());
                int arg2 = Integer.parseInt(stack.pop());
                int res = 0;
                if ("+".equals(token))
                    res = arg1 + arg2;
                else if ("-".equals(token))
                    res = arg1 - arg2;
                else if ("*".equals(token))
                    res = arg1 * arg2;
                else if ("/".equals(token))
                    res = arg1 / arg2;
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

    private boolean isOperator(char symbol) {
        return (symbol == '+' || symbol == '-' || symbol == '*' || symbol == '/');
    }
}
