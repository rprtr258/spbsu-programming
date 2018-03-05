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
        switch (data) {
            case "+":
                return leftValue + rightValue;
            case "-":
                return leftValue - rightValue;
            case "*":
                return leftValue * rightValue;
            case "/":
                return leftValue / rightValue;
            default:
                throw new IllegalStateException();
        }
    }

    public String infixForm(boolean printBrackets) {
        String result = left.infixForm(true) + " " + data + " " + right.infixForm(true);
        if (printBrackets)
            result = String.format("(%s)", result);
        return result;
    }

    public String toString() {
        return String.format("(%s %s %s)", data, left.toString(), right.toString());
    }
}
