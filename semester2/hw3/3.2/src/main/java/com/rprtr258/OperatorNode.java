package com.rprtr258;

public class OperatorNode extends TreeNode {
    public OperatorNode(String data, TreeNode left, TreeNode right) {
        super(data);
        this.left = left;
        this.right = right;
    }

    public int calc() {
        int leftValue = left.calc();
        int rightValue = right.calc();
        if ("+".equals(data))
            return leftValue + rightValue;
        if ("-".equals(data))
            return leftValue - rightValue;
        if ("*".equals(data))
            return leftValue * rightValue;
        if ("/".equals(data))
            return leftValue / rightValue;
        throw new IllegalStateException();
    }

    public String toString() {
        return String.format("(%s %s %s)", data, left.toString(), right.toString());
    }
}
