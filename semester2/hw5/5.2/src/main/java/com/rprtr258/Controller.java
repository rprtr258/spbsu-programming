package com.rprtr258;

import javafx.beans.value.ChangeListener;
import javafx.scene.control.*;

/**
 * JavaFX elements controller class.
 */
public class Controller {
    public Spinner<Integer> firstOperandSpinner;
    public Spinner<Integer> secondOperandSpinner;
    public TextField resultField;
    public ChoiceBox<String> operatorChoice;

    /**
     * Initializer function. Sets default values and listeners to objects.
     */
    public void initialize() {
        initializeObjects();
        setListeners();
    }

    /**
     * Sets default values to all objects.
     */
    private void initializeObjects() {
        firstOperandSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(Integer.MIN_VALUE, Integer.MAX_VALUE, 0, 1));
        firstOperandSpinner.getValueFactory().setValue(0);
        secondOperandSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(Integer.MIN_VALUE, Integer.MAX_VALUE, 0, 1));
        secondOperandSpinner.getValueFactory().setValue(0);
        operatorChoice.getItems().addAll("+", "-", "*", "/");
    }

    /**
     * Sets listeners to all objects.
     */
    private void setListeners() {
        ChangeListener<? super Integer> changeResult = (observable, oldValue, newValue) -> displayCalculationResult();
        firstOperandSpinner.valueProperty().addListener(changeResult);
        secondOperandSpinner.valueProperty().addListener(changeResult);
        operatorChoice.valueProperty().addListener((observable, oldValue, newValue) -> displayCalculationResult());
    }

    /**
     * Evaluates and displays result in text field.
     */
    private void displayCalculationResult() {
        resultField.setText(evaluateResult());
    }

    /**
     * Evaluates result as string or returns message in some cases.
     * @return result of evaluation if operands and operand are correct,
     *         "Choose action" if operation not set.
     */
    private String evaluateResult() {
        if (operatorChoice.getValue() == null)
            return "Choose action";
        Double first = new Double(firstOperandSpinner.getValue());
        Double second = new Double(secondOperandSpinner.getValue());
        Double result = 0.0;
        switch (operatorChoice.getValue()) {
            case "+": {
                result = first + second;
                break;
            }
            case "-": {
                result = first - second;
                break;
            }
            case "*": {
                result = first * second;
                break;
            }
            case "/": {
                result = first / second;
                break;
            }
        }
        return result.toString();
    }
}
