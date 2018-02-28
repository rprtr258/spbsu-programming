package com.rprtr258;

public abstract class TreeNode {
    protected TreeNode left = null;
    protected TreeNode right = null;
    protected String data;

    public TreeNode(String data) {
        this.data = data;
    }

    public abstract int calc();
    public abstract String infixForm(boolean printBrackets);
}
