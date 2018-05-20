package com.rprtr258.client;

import com.rprtr258.network.Client;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

// TODO: stop Socket thread on close
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
        playerNameLabel.setText("");
    }

    public void setPlayerName(String playerName) {
        mark = playerName;
        playerNameLabel.setText("You are player " + playerName);
        setButtonsDisable(false);
        if ("O".equals(mark)) {
            onTurnWaiting();
        } else {
            gameStatusLabel.setText("Waiting for your turn.");
        }
    }

    public void setClient(Client client) {
        this.thisClient = client;
        thisClient.setOnLostConnection(this::onLostServerConnection);
    }

    private void makeMove(int row, int column) {
        if (isWaitingForOpponentTurn)
            return;
        thisClient.makeMove(row, column, () -> onSuccessTurn(row, column), this::onLostServerConnection, this::onGameEnd);
    }

    private void onSuccessTurn(int row, int column) {
        setButtonText(row, column, mark);
        onTurnWaiting();
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

    private void onGameEnd(String winner) {
        switch (winner) {
            case "draw": {
                gameStatusLabel.setText("Draw!");
                break;
            }
            case "X": {
                gameStatusLabel.setText("First player won!");
                break;
            }
            case "O": {
                gameStatusLabel.setText("Second player won!");
                break;
            }
        }
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
