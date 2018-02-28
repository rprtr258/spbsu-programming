package com.rprtr258;

public class NumberNode extends TreeNode {
    public NumberNode(String data) {
        super(data);
    }

    public int calc() {
        return Integer.parseInt(data);
    }

    public String infixForm(boolean printBrackets) {
        return data;
    }

    public String toString() {
        return data;
    }
}
