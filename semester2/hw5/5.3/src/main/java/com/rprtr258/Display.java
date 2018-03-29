package com.rprtr258;

import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

/**
 * Class which manipulates and controls calculator's text area and status label.
 */
public class Display {
    private TextArea displayArea;
    private Label statusLabel;

    /**
     * Constructor which initializes text area and status label.
     * @param display text area which will display result and which display input.
     * @param statusLabel status label which displays status in certain situations.
     */
    public Display(TextArea display, Label statusLabel) {
        this.displayArea = display;
        this.statusLabel = statusLabel;
    }

    /**
     * Adds symbol to end of display's text.
     * @param symbol char to add.
     */
    public void append(char symbol) {
        displayArea.setText(displayArea.getText() + symbol);
        statusLabel.setText("");
    }

    /**
     * Clears display.
     */
    public void clearDisplay() {
        displayArea.setText("");
        statusLabel.setText("");
    }

    /**
     * Swaps expression on display with evaluated result.
     * Shows message in status label if expression is incorrect.
     */
    public void showResult() {
        String expression = displayArea.getText();
        if (Expression.checkCorrectness(expression)) {
            try {
                double result = Expression.evaluate(expression);
                displayArea.setText(String.format("%.6f", result));
                statusLabel.setText("");
            } catch (InvalidExpressionException e) {
                // Can't happen because expression correctness checked.
                e.printStackTrace();
            }
        } else {
            statusLabel.setText("Incorrect expression.");
        }
    }

    /**
     * Removes one last char from display.
     * Shows message in status label if display is already empty.
     */
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
