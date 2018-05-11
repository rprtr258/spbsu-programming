package com.rprtr258.client;

import com.rprtr258.network.SocketWrapper;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

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

    private SocketWrapper socketWrapper = null;

    public void initialize() {
        Button buttons[][] = {{button00, button01, button02},
                              {button10, button11, button12},
                              {button20, button21, button22}};
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                int finalI = i;
                int finalJ = j;
                buttons[i][j].setOnAction((actionEvent) -> makeMove(actionEvent, finalI, finalJ));
            }
        }
        try {
            socketWrapper = new SocketWrapper(new Socket("localhost", 12345));
            socketWrapper.sendMessage(String.format("connect %s", System.nanoTime()));
        } catch (IOException e) {
            e.printStackTrace();
            // TODO: overthink
            System.exit(-1);
        }
    }

    private void makeMove(ActionEvent actionEvent, int row, int column) {
        socketWrapper.sendMessage(String.format("turn %d %d", row, column));
        try {
            String response = socketWrapper.readMessage();
            switch (response) {
                case "ok": {
                    ((Button)actionEvent.getSource()).setText("X");
                    break;
                }
                case "no": {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
