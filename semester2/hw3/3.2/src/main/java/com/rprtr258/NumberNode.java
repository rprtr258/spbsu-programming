package com.rprtr258;

public class NumberNode extends TreeNode {
    @Override
    public int calc() {
        return Integer.parseInt(data);
    }

    @Override
    public String infixForm(boolean printBrackets) {
        return data;
    }
    
    @Override
    public String toString() {
        return data;
    }
}
