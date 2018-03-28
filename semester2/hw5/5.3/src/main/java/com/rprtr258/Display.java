package com.rprtr258;

import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class Display {
    private TextArea displayArea;
    private Label statusLabel;

    public Display(TextArea display, Label statusLabel) {
        this.displayArea = display;
        this.statusLabel = statusLabel;
    }

    public void append(char symbol) {
        displayArea.setText(displayArea.getText() + symbol);
        statusLabel.setText("");
    }

    public void clearDisplay() {
        displayArea.setText("");
        statusLabel.setText("");
    }

    public void showResult() {
        String expression = displayArea.getText();
        if (Expression.checkCorrectness(expression)) {
            try {
                double result = Expression.evaluate(expression);
                displayArea.setText(String.format("%.6f", result));
                statusLabel.setText("");
            } catch (InvalidExpressionException e) {
                e.printStackTrace();
            }
        } else {
            statusLabel.setText("Incorrect expression.");
        }
    }

    public void deleteSymbol() {
        String displayText = displayArea.getText();
        if (!"".equals(displayText)) {
            displayArea.setText(displayText.substring(0, displayText.length() - 1));
            statusLabel.setText("");
        } else {
            statusLabel.setText("Display is already empty.");
        }
    }
}
