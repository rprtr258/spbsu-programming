package com.rprtr258;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

/**
 * Controller class.
 */
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

    private Alert alert = null;

    /**
     * Initializes buttons with listeners.
     */
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

        alert = new Alert(Alert.AlertType.INFORMATION, "%s has won!", ButtonType.NEXT);
        alert.setOnCloseRequest((e) -> {
            game.restart();
            clearButtonsLabels();
        });
    }

    /**
     * Action that will happen when button with given coordinates is clicked.
     * @param source action event source.
     * @param i row of button.
     * @param j column of button.
     */
    private void buttonAction(Object source, int i, int j) {
        boolean madeTurn = game.makeTurn(i, j);
        if (madeTurn) {
            CellState cellState = game.getCellState(i, j);
            ((Button)source).setText(cellState == CellState.ZERO_CELL ? "O" : "X");

            GameState gameState = game.getState();
            if (gameState == GameState.CROSS_WIN || gameState == GameState.ZERO_WIN || gameState == GameState.DRAW)
                endGame();
        }
    }

    /**
     * Shows alert if game ended.
     */
    private void endGame() {
        if (game.getState() == GameState.CROSS_WIN) {
            alert.setContentText("First player has won!");
            alert.show();
        }
        if (game.getState() == GameState.ZERO_WIN) {
            alert.setContentText("Second player has won!");
            alert.show();
        }
        if (game.getState() == GameState.DRAW) {
            alert.setContentText("Draw!");
            alert.show();
        }
    }

    /**
     * Clears buttons labels.
     */
    private void clearButtonsLabels() {
        button00.setText("");
        button01.setText("");
        button02.setText("");
        button10.setText("");
        button11.setText("");
        button12.setText("");
        button20.setText("");
        button21.setText("");
        button22.setText("");
    }
}
