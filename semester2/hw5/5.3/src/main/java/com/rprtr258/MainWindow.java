package com.rprtr258;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.util.StringTokenizer;

public class MainWindow {
    public Button button0;
    public Button button1;
    public Button button2;
    public Button button3;
    public Button button4;
    public Button button5;
    public Button button6;
    public Button button7;
    public Button button8;
    public Button button9;

    public Button buttonComma;

    public Button buttonDivide;
    public Button buttonMultiply;
    public Button buttonMinus;
    public Button buttonPlus;

    public Button buttonEvaluate;

    public Button buttonDelete;
    public Button buttonClearDisplay;

    public TextArea display;
    public Label statusLabel;

    public void initialize() {
        setDigitsListeners();
        setOperatorsListeners();
        setClearDisplayListener();
        setEvaluateListener();
    }

    private void setDigitsListeners() {
        button0.setOnAction((actionEvent) -> appendSymbol('0'));
        button1.setOnAction((actionEvent) -> appendSymbol('1'));
        button2.setOnAction((actionEvent) -> appendSymbol('2'));
        button3.setOnAction((actionEvent) -> appendSymbol('3'));
        button4.setOnAction((actionEvent) -> appendSymbol('4'));
        button5.setOnAction((actionEvent) -> appendSymbol('5'));
        button6.setOnAction((actionEvent) -> appendSymbol('6'));
        button7.setOnAction((actionEvent) -> appendSymbol('7'));
        button8.setOnAction((actionEvent) -> appendSymbol('8'));
        button9.setOnAction((actionEvent) -> appendSymbol('9'));
        buttonComma.setOnAction((actionEvent) -> appendSymbol(','));
    }

    private void setClearDisplayListener() {
        buttonClearDisplay.setOnAction((actionEvent) -> clearDisplay());
        buttonDelete.setOnAction((actionEvent) -> deleteLastSymbol());
    }

    private void setOperatorsListeners() {
        buttonPlus.setOnAction((actionEvent) -> appendSymbol('+'));
        buttonMinus.setOnAction((actionEvent) -> appendSymbol('-'));
        buttonMultiply.setOnAction((actionEvent) -> appendSymbol('*'));
        buttonDivide.setOnAction((actionEvent) -> appendSymbol('/'));
    }

    private void appendSymbol(char symbol) {
        display.setText(display.getText() + symbol);
    }

    private void setEvaluateListener() {
        buttonEvaluate.setOnAction((actionEvent) -> displayResult());
    }

    private void displayResult() {
        String expression = display.getText();
        if ("".equals(expression)) {
            display.setText("0");
            return;
        }
        String numberRegexp = "([1-9]\\d*|0)(,\\d*)?";
        String operatorRegexp = "[+\\-*/]";
        if (expression.matches(String.format("(-%s%s)?(%s%s)*%s", numberRegexp, operatorRegexp, numberRegexp, operatorRegexp, numberRegexp))) {
            double value = evaluate(expression);
            display.setText(String.format("%.6f", value));
            statusLabel.setText("");
            return;
        }
        statusLabel.setText("Incorrect expression.");
    }

    private double evaluate(String expression) {
        expression = expression.replace(',', '.');
        boolean minus = false;
        if (expression.charAt(0) == '-') {
            minus = true;
            expression = expression.substring(1);
        }
        StringTokenizer stringTokenizer = new StringTokenizer(expression, "+-", true);
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

    private double evaluateMultiplication(String expression) {
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

    private void clearDisplay() {
        display.setText("");
    }

    private void deleteLastSymbol() {
        String displayText = display.getText();
        if (!"".equals(displayText))
            display.setText(displayText.substring(0, displayText.length() - 1));
    }
}
