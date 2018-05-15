package com.rprtr258.client;

import com.rprtr258.network.MessagesProcessor;
import com.rprtr258.network.SocketWrapper;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.Socket;

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
    private SocketWrapper socketWrapper = null;
    private Button buttons[][] = null;
    private boolean isWaitingForOpponentTurn = false;

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
        try {
            connectToServer("localhost", 12345);
        } catch (IOException e) {
            disableButtons();
            playerNameLabel.setText("You are not playing :(");
            gameStatusLabel.setText("Couldn't connect to server.");
        }
    }

    // TODO: don't connect in initialize, connect in second thread in order to show window while connecting
    // TODO: add button to retry connect?
    private void connectToServer(String hostname, int port) throws IOException {
        socketWrapper = new SocketWrapper(new Socket(hostname, port));
        socketWrapper.sendMessage("connect");
        String response = socketWrapper.readMessage();
        mark = response.substring(response.indexOf(' ') + 1);
        playerNameLabel.setText("You are player " + mark);
        if ("O".equals(mark))
            waitOpponentTurn();
        else
            gameStatusLabel.setText("Waiting for your turn.");
    }

    private void makeMove(int row, int column) {
        if (isWaitingForOpponentTurn)
            return;
        socketWrapper.sendMessage(String.format("turn %d %d", row, column));
        try {
            String response = socketWrapper.readMessage();
            if ("success".equals(response)) {
                buttons[row][column].setText(mark);
                waitOpponentTurn();
            }
        } catch (IOException e) {
            onLostServerConnection();
        }
    }

    private void waitOpponentTurn() {
        isWaitingForOpponentTurn = true;
        gameStatusLabel.setText("Waiting for " + ("X".equals(mark) ? "O" : "X") + "'s turn.");
        new Thread(new Task<Void>() {
            @Override
            protected Void call() {
                try {
                    String opponentTurn = socketWrapper.waitMessageMatching(MessagesProcessor.OPPONENT_TURN_REGEXP);
                    int i = MessagesProcessor.parseRow(opponentTurn);
                    int j = MessagesProcessor.parseColumn(opponentTurn);
                    Platform.runLater(() -> setButtonText(i, j, "X".equals(mark) ? "O" : "X"));
                    isWaitingForOpponentTurn = false;
                    Platform.runLater(() -> gameStatusLabel.setText("Waiting for your turn."));
                } catch (IOException e) {
                    Platform.runLater(() -> onLostServerConnection());
                }
                return null;
            }
        }).start();
    }

    private void setButtonText(int i, int j, String s) {
        buttons[i][j].setText(s);
    }

    private void onLostServerConnection() {
        disableButtons();
        gameStatusLabel.setText("Lost connection to server :C");
    }

    private void disableButtons() {
        for (Button[] buttonsRow : buttons)
            for (Button button : buttonsRow)
                button.setDisable(true);
    }
}
