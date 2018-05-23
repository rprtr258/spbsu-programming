package com.rprtr258.client;

import com.rprtr258.network.Client;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class MainWindowController {
    public Button button00;
    public Button button01;
    public Button button02;
    public Button button10;
    public Button button11;
    public Button button12;
    public Button button20;
    public Button button21;
    public Button button22;
    public Label gameStatusLabel;
    public Label playerNameLabel;
    public Button restartButton;

    private String mark = null;
    private Button buttons[][] = null;
    private boolean isWaitingForOpponentTurn = false;
    private Client thisClient = null;

    public void initialize() {
        buttons = new Button[][]{{button00, button01, button02},
                                 {button10, button11, button12},
                                 {button20, button21, button22}};
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                final int finalI = i;
                final int finalJ = j;
                buttons[i][j].setOnAction((actionEvent) -> makeMove(finalI, finalJ));
            }
        }
        restartButton.setOnAction((actionEvent) -> onRestartRequest());
        playerNameLabel.setText("");
        setButtonsDisable(true);
        restartButton.setDisable(true);
    }

    public void configure(String playerName, Client client) {
        this.thisClient = client;
        thisClient.configure(e -> onLostServerConnection(), this::onDisconnect);
        mark = playerName;
        playerNameLabel.setText("You are player " + playerName);
        setButtonsDisable(false);
        if ("O".equals(mark)) {
            onTurnWaiting();
        } else {
            gameStatusLabel.setText("Waiting for your turn.");
        }
    }

    private void makeMove(int row, int column) {
        if (isWaitingForOpponentTurn)
            return;
        thisClient.makeMove(row, column, () -> onSuccessTurn(row, column), this::onGameContinuing, this::onGameEnd);
    }

    private void onSuccessTurn(int row, int column) {
        setButtonText(row, column, mark);
    }

    private void onGameContinuing() {
        onTurnWaiting();
    }

    private void onRestartRequest() {
        for (Button[] buttonsRow : buttons) {
            for (Button button : buttonsRow) {
                button.setText("");
            }
        }
        playerNameLabel.setText("");
        gameStatusLabel.setText("Waiting for other player to confirm");
        setButtonsDisable(true);
        restartButton.setDisable(true);
        thisClient.restart(this::onRestart);
    }

    private void onRestart(String playerName) {
        mark = playerName;
        playerNameLabel.setText("You are player " + playerName);
        setButtonsDisable(false);
        if ("O".equals(mark)) {
            onTurnWaiting();
        } else {
            gameStatusLabel.setText("Waiting for your turn.");
        }
    }

    private void onOpponentTurn(int row, int column) {
        isWaitingForOpponentTurn = false;
        setButtonText(row, column, "X".equals(mark) ? "O" : "X");
        gameStatusLabel.setText("Waiting for your turn.");
    }

    private void onTurnWaiting() {
        gameStatusLabel.setText("Waiting for " + ("X".equals(mark) ? "O" : "X") + "'s turn.");
        isWaitingForOpponentTurn = true;
        thisClient.waitGameChanges(this::onOpponentTurn, this::onGameEnd);
    }

    private void onDisconnect() {
        gameStatusLabel.setText("Opponent has disconnected");
        setButtonsDisable(true);
        restartButton.setDisable(true);
    }

    private void onGameEnd(String winner) {
        if ("draw".equals(winner)) {
            gameStatusLabel.setText("Draw!");
        } else if (mark.equals(winner)) {
            gameStatusLabel.setText("You won!");
        } else {
            gameStatusLabel.setText("You lost!");
        }
        restartButton.setDisable(false);
    }

    private void setButtonText(int i, int j, String s) {
        buttons[i][j].setText(s);
    }

    private void onLostServerConnection() {
        setButtonsDisable(true);
        gameStatusLabel.setText("Lost connection to server :C");
    }

    private void setButtonsDisable(boolean value) {
        for (Button[] buttonsRow : buttons)
            for (Button button : buttonsRow)
                button.setDisable(value);
    }
}
