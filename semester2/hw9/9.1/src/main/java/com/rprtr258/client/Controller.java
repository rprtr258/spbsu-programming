package com.rprtr258.client;

import com.rprtr258.network.Client;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

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
    public Label gameStatusLabel;
    public Label playerNameLabel;

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
        setButtonsDisable(true);
        thisClient = new Client();
        thisClient.tryConnectServer("localhost", 12345, (playerName) -> {
            mark = playerName;
            playerNameLabel.setText("You are player " + playerName);
            setButtonsDisable(false);
            if ("O".equals(mark)) {
                isWaitingForOpponentTurn = true;
                gameStatusLabel.setText("Waiting for " + ("X".equals(mark) ? "O" : "X") + "'s turn.");
                thisClient.waitOpponentTurn((i, j) -> {
                    isWaitingForOpponentTurn = false;
                    setButtonText(i, j, "X".equals(mark) ? "O" : "X");
                    gameStatusLabel.setText("Waiting for your turn.");
                }, this::onLostServerConnection);
            } else
                gameStatusLabel.setText("Waiting for your turn.");
        }, () -> {
            setButtonsDisable(false);
            playerNameLabel.setText("You are not playing :(");
            gameStatusLabel.setText("Couldn't connect to server.");
        });
    }

    private void makeMove(int row, int column) {
        if (isWaitingForOpponentTurn)
            return;
        thisClient.makeMove(row, column, () -> {
            buttons[row][column].setText(mark);
            gameStatusLabel.setText("Waiting for " + ("X".equals(mark) ? "O" : "X") + "'s turn.");
            isWaitingForOpponentTurn = true;
            thisClient.waitOpponentTurn((i, j) -> {
                        isWaitingForOpponentTurn = false;
                        setButtonText(i, j, "X".equals(mark) ? "O" : "X");
                        gameStatusLabel.setText("Waiting for your turn.");
                    }, this::onLostServerConnection);
        }, this::onLostServerConnection);
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
