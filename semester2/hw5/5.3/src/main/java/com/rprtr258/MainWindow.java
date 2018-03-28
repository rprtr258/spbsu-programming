package com.rprtr258;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

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

    public TextArea displayTextArea;
    public Label statusLabel;

    private Display display = null;

    public void initialize() {
        display = new Display(displayTextArea, statusLabel);
        setDigitsAndCommaButtonsListeners();
        setOperatorButtonsListeners();
        setClearButtonsListeners();
        setEvaluateButtonListener();
    }

    private void setDigitsAndCommaButtonsListeners() {
        button0.setOnAction((actionEvent) -> display.append('0'));
        button1.setOnAction((actionEvent) -> display.append('1'));
        button2.setOnAction((actionEvent) -> display.append('2'));
        button3.setOnAction((actionEvent) -> display.append('3'));
        button4.setOnAction((actionEvent) -> display.append('4'));
        button5.setOnAction((actionEvent) -> display.append('5'));
        button6.setOnAction((actionEvent) -> display.append('6'));
        button7.setOnAction((actionEvent) -> display.append('7'));
        button8.setOnAction((actionEvent) -> display.append('8'));
        button9.setOnAction((actionEvent) -> display.append('9'));
        buttonComma.setOnAction((actionEvent) -> display.append(','));
    }

    private void setClearButtonsListeners() {
        buttonClearDisplay.setOnAction((actionEvent) -> display.clearDisplay());
        buttonDelete.setOnAction((actionEvent) -> display.deleteSymbol());
    }

    private void setOperatorButtonsListeners() {
        buttonPlus.setOnAction((actionEvent) -> display.append('+'));
        buttonMinus.setOnAction((actionEvent) -> display.append('-'));
        buttonMultiply.setOnAction((actionEvent) -> display.append('*'));
        buttonDivide.setOnAction((actionEvent) -> display.append('/'));
    }

    private void setEvaluateButtonListener() {
        buttonEvaluate.setOnAction((actionEvent) -> display.showResult());
    }
}
