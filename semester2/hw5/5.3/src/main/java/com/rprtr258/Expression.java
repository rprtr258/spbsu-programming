package com.rprtr258;

import java.util.StringTokenizer;

/**
 * Static class which can check expression for correctness and evaluate correct expression.
 */
public class Expression {
    /**
     * Checks if given expression is correct and can be evaluated.
     * @param expression expression to check.
     * @return true if expression can be evaluated.
     */
    public static boolean checkCorrectness(String expression) {
        if ("".equals(expression)) {
            return true;
        }
        String numberRegexp = "([1-9]\\d*|0)(\\.\\d*)?";
        String operatorRegexp = "[+\\-*/]";
        return expression.matches(String.format("(-%s%s)?(%s%s)*%s", numberRegexp, operatorRegexp, numberRegexp, operatorRegexp, numberRegexp));
    }

    /**
     * Evaluates given expression.
     * @param expression expression to calculate.
     * @return result of given expression.
     * @throws InvalidExpressionException if given expression is incorrect.
     */
    public static double evaluate(String expression) throws InvalidExpressionException {
        if (!checkCorrectness(expression))
            throw new InvalidExpressionException();

        if ("".equals(expression))
            return 0.0;

        String formattedExpression = expression.replace(',', '.');
        StringTokenizer stringTokenizer = new StringTokenizer(formattedExpression, "+-", true);
        double result = 0.0;
        if (expression.charAt(0) == '-') {
            stringTokenizer.nextToken();
            result = evaluateMultiplication(stringTokenizer.nextToken());
            result *= -1;
        } else {
            result = evaluateMultiplication(stringTokenizer.nextToken());
        }
        double sign = 0.0;
        while (stringTokenizer.hasMoreTokens()) {
            String token = stringTokenizer.nextToken();
            switch (token) {
                case "+": {
                    sign = 1;
                    break;
                }
                case "-": {
                    sign = -1;
                    break;
                }
                default: {
                    double accumulator = evaluateMultiplication(token);
                    result += sign * accumulator;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Evaluates token which does not contain '+' and '-'.
     * @param expressionToken token with only divisions and multiplications.
     * @return result of calculation given token.
     */
    private static double evaluateMultiplication(String expressionToken) {
        StringTokenizer stringTokenizer = new StringTokenizer(expressionToken, "*/", true);
        double result = Double.parseDouble(stringTokenizer.nextToken());
        boolean multiply = false;
        while (stringTokenizer.hasMoreTokens()) {
            String token = stringTokenizer.nextToken();
            switch (token) {
                case "*": {
                    multiply = true;
                    break;
                }
                case "/": {
                    multiply = false;
                    break;
                }
                default: {
                    double accumulator = Double.parseDouble(token);
                    result = (multiply ? result * accumulator : result / accumulator);
                    break;
                }
            }
        }
        return result;
    }
}
