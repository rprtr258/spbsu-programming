package com.rprtr258;

import javafx.scene.control.Button;

public class Controller {
    public Button button00;
    public Button button01;
    public Button button02;
    public Button button10;
    public Button button11;
    public Button button12;
    public Button button20;
    public Button button21;
    public Button button22;
    private TicTacToe game = null;

    public void initialize() {
        game = new TicTacToe();
        button00.setOnAction((actionEvent) -> buttonAction(actionEvent.getSource(), 0, 0));
        button01.setOnAction((actionEvent) -> buttonAction(actionEvent.getSource(), 0, 1));
        button02.setOnAction((actionEvent) -> buttonAction(actionEvent.getSource(), 0, 2));
        button10.setOnAction((actionEvent) -> buttonAction(actionEvent.getSource(), 1, 0));
        button11.setOnAction((actionEvent) -> buttonAction(actionEvent.getSource(), 1, 1));
        button12.setOnAction((actionEvent) -> buttonAction(actionEvent.getSource(), 1, 2));
        button20.setOnAction((actionEvent) -> buttonAction(actionEvent.getSource(), 2, 0));
        button21.setOnAction((actionEvent) -> buttonAction(actionEvent.getSource(), 2, 1));
        button22.setOnAction((actionEvent) -> buttonAction(actionEvent.getSource(), 2, 2));
    }

    private void buttonAction(Object source, int i, int j) {
        boolean madeTurn = game.makeTurn(i, j);
        if (madeTurn) {
            CellState cellState = game.getCellState(i, j);
            ((Button)source).setText(cellState == CellState.ZERO_CELL ? "O" : "X");
        }
    }
}
