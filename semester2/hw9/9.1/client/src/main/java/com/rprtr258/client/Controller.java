package com.rprtr258.client;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

import java.io.*;
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

    private Socket socket = null;
    private InputStream in = null;
    private OutputStream out = null;

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
            socket = new Socket("localhost", 12345);
            out = socket.getOutputStream();
            in = socket.getInputStream();
            sendToServer(String.format("client %d", System.nanoTime()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void makeMove(ActionEvent actionEvent, int row, int column) {
        try {
            sendToServer(String.format("turn %d %d", row, column));
            String response = readServerResponse();
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

    private void sendToServer(String message) throws IOException {
        out.write((message + "\n").getBytes());
        out.flush();
        System.out.printf("Client: Sent \"%s\"\n", message);
    }

    private String readServerResponse() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String result = br.readLine();
        System.out.printf("Client: Received \"%s\"\n", result);
        return result;
    }
}
