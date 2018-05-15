package com.rprtr258.client;

import com.rprtr258.network.SocketWrapper;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
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
    private boolean waitingForOpponentTurn = false;

    public void initialize() {
        buttons = new Button[][]{{button00, button01, button02},
                {button10, button11, button12},
                {button20, button21, button22}};
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                final int finalI = i;
                final int finalJ = j;
                buttons[i][j].setOnAction((actionEvent) -> makeMove(actionEvent, finalI, finalJ));
            }
        }
        try {
            connectToServer("localhost", 12345);
        } catch (IOException e) {
            for (Button[] buttonsRow : buttons)
                for (Button button : buttonsRow)
                    button.setDisable(true);
            playerNameLabel.setText("You are not playing :(");
            gameStatusLabel.setText("Couldn't connect to server.");
        }
    }

    // TODO: don't connect in initialize, connect in second thread in order to show window while connecting
    private void connectToServer(String hostname, int port) throws IOException {
        socketWrapper = new SocketWrapper(new Socket(hostname, port));
        socketWrapper.sendMessage("connect");
        String response = socketWrapper.readMessage();
        mark = response.substring(response.indexOf(' ') + 1);
        playerNameLabel.setText("You are player " + mark);
        if ("O".equals(mark))
            waitOpponentTurn();
    }

    private void makeMove(ActionEvent actionEvent, int row, int column) {
        if (waitingForOpponentTurn)
            return;
        socketWrapper.sendMessage(String.format("turn %d %d", row, column));
        try {
            String response = socketWrapper.readMessage();
            if ("success".equals(response)) {
                ((Button)actionEvent.getSource()).setText(mark);
                waitOpponentTurn();
            }
        } catch (IOException e) {
            gameStatusLabel.setText("Lost connection to server :C");
        }
    }

    private void waitOpponentTurn() {
        waitingForOpponentTurn = true;
        new Thread(new Task<Void>() {
            @Override
            protected Void call() {
                try {
                    String opponentTurn = socketWrapper.waitMessageMatching("opturn [0-2] [0-2]");
                    int i = Integer.parseInt(opponentTurn.substring(opponentTurn.indexOf(' ') + 1, opponentTurn.lastIndexOf(' ')));
                    int j = Integer.parseInt(opponentTurn.substring(opponentTurn.lastIndexOf(' ') + 1));
                    Platform.runLater(() -> setButtonText(i, j, "X".equals(mark) ? "O" : "X"));
                    waitingForOpponentTurn = false;
                } catch (IOException e) {
                    Platform.runLater(() -> gameStatusLabel.setText("Lost connection to server :C"));
                }
                return null;
            }
        }).start();
    }

    private void setButtonText(int i, int j, String s) {
        buttons[i][j].setText(s);
    }
}
