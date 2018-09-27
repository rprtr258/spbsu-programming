package com.rprtr258;

public class ExpressionTree {
    private TreeNode root = null;

    public ExpressionTree(String expression) {
        ReferenceInt index = new ReferenceInt(0);
        root = buildNode(expression, index);
    }

    public String toString() {
        return root.toString();
    }

    public String infixForm() {
        return root.infixForm(false);
    }

    public int calc() {
        return root.calc();
    }

    private TreeNode buildNode(String expression, ReferenceInt i) {
        StringBuilder numberToken = new StringBuilder();
        while (!Character.isDigit(expression.charAt(i.get())) && !isOperator(expression.charAt(i.get())))
            i.inc();
        char symbol = expression.charAt(i.get());
        if (isOperator(symbol)) {
            i.inc();
            TreeNode leftNode = buildNode(expression, i);
            TreeNode rightNode = buildNode(expression, i);
            return new OperatorNode(String.valueOf(symbol), leftNode, rightNode);
        } else if (Character.isDigit(symbol)) {
            while (Character.isDigit(expression.charAt(i.get()))) {
                numberToken.append(expression.charAt(i.get()));
                i.inc();
            }
            return new NumberNode(numberToken.toString());
        }
        throw new IllegalStateException();
    }

    private boolean isOperator(char symbol) {
        return (symbol == '+' || symbol == '-' || symbol == '*' || symbol == '/');
    }
}
