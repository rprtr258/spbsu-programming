package com.rprtr258;

import java.util.StringTokenizer;

public class Expression {
    public static boolean checkCorrectness(String expression) {
        if ("".equals(expression)) {
            return true;
        }
        String numberRegexp = "([1-9]\\d*|0)(\\.\\d*)?";
        String operatorRegexp = "[+\\-*/]";
        return expression.matches(String.format("(-%s%s)?(%s%s)*%s", numberRegexp, operatorRegexp, numberRegexp, operatorRegexp, numberRegexp));
    }

    public static double evaluate(String expression) throws InvalidExpressionException {
        if (!checkCorrectness(expression))
            throw new InvalidExpressionException();

        if ("".equals(expression))
            return 0.0;

        String formattedExpression = expression.replace(',', '.');
        boolean minus = false;
        if (expression.charAt(0) == '-') {
            minus = true;
            formattedExpression = expression.substring(1);
        }
        StringTokenizer stringTokenizer = new StringTokenizer(formattedExpression, "+-", true);
        double result = evaluateMultiplication(stringTokenizer.nextToken());
        if (minus)
            result *= -1;
        double sign = 0.0;
        while (stringTokenizer.hasMoreTokens()) {
            String token = stringTokenizer.nextToken();
            if ("+".equals(token)) {
                sign = 1;
            } else if ("-".equals(token)) {
                sign = -1;
            } else if (!"".equals(token)) {
                double accumulator = evaluateMultiplication(token);
                result += sign * accumulator;
            }
        }
        return result;
    }

    private static double evaluateMultiplication(String expression) {
        StringTokenizer stringTokenizer = new StringTokenizer(expression, "*/", true);
        double result = Double.parseDouble(stringTokenizer.nextToken());
        boolean multiply = false;
        while (stringTokenizer.hasMoreTokens()) {
            String token = stringTokenizer.nextToken();
            if ("*".equals(token)) {
                multiply = true;
            } else if ("/".equals(token)) {
                multiply = false;
            } else if (!"".equals(token)) {
                double accumulator = Double.parseDouble(token);
                result = (multiply ? result * accumulator : result / accumulator);
            }
        }
        return result;
    }
}
