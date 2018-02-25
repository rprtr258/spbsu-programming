package com.rprtr258;

import java.util.ArrayList;

public class Expression {
    private ArrayList<String> tokens;

    public Expression(String expr) {
        tokens = new ArrayList<>();
        String token = "";
        for (int i = 0; i < expr.length(); i++) {
            char symbol = expr.charAt(i);
            if (symbol == '+' || symbol == '-' || symbol == '*' || symbol == '/') {
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
        return Integer.parseInt(stack.top());
    }
}
