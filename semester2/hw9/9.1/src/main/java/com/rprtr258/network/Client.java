package com.rprtr258.network;

import javafx.application.Platform;
import javafx.concurrent.Task;

import java.io.IOException;
import java.net.Socket;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Client {
    private SocketWrapper socketWrapper = null;

    public void tryConnectServer(String host, int port, Consumer<String> onConnect, Runnable onLostConnection) {
        new Thread(new Task<Void>() {
            @Override
            protected Void call() {
                try {
                    String playerName = connectToServer(host, port);
                    Platform.runLater(() -> onConnect.accept(playerName));
                } catch (IOException e) {
                    // TODO: add button to retry connect?
                    Platform.runLater(onLostConnection);
                }
                return null;
            }
        }).start();
    }

    private String connectToServer(String hostname, int port) throws IOException {
        socketWrapper = new SocketWrapper(new Socket(hostname, port));
        socketWrapper.sendMessage("connect");
        String response = socketWrapper.readMessage();
        return response.substring(response.indexOf(' ') + 1);
    }

    public void makeMove(int row, int column, Runnable onSuccess, Runnable onLostConnection) {
        socketWrapper.sendMessage(String.format("turn %d %d", row, column));
        try {
            String response = socketWrapper.readMessage();
            if ("success".equals(response)) {
                onSuccess.run();
            }
        } catch (IOException e) {
            onLostConnection.run();
        }
    }

    public void waitOpponentTurn(BiConsumer<Integer, Integer> onOpponentTurn, Runnable onLostConnection) {
        new Thread(new Task<Void>() {
            @Override
            protected Void call() {
                try {
                    String opponentTurn = socketWrapper.waitMessageMatching(MessagesProcessor.OPPONENT_TURN_REGEXP);
                    int i = MessagesProcessor.parseRow(opponentTurn);
                    int j = MessagesProcessor.parseColumn(opponentTurn);
                    Platform.runLater(() -> onOpponentTurn.accept(i, j));
                } catch (IOException e) {
                    Platform.runLater(onLostConnection);
                }
                return null;
            }
        }).start();
    }
}
