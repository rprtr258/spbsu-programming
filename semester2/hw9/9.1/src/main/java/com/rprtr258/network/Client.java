package com.rprtr258.network;

import javafx.application.Platform;
import javafx.concurrent.Task;

import java.io.IOException;
import java.net.Socket;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Client {
    private SocketWrapper socketWrapper = null;
    private Runnable onLostConnection = null;

    public Client(Runnable onLostConnection) {
        this.onLostConnection = onLostConnection;
    }

    public void tryConnectServer(String host, int port, Consumer<String> onConnect, Runnable onFailConnection) {
        new Thread(new Task<Void>() {
            @Override
            protected Void call() {
                try {
                    socketWrapper = new SocketWrapper(new Socket(host, port));
                    String connectRequest = MessagesProcessor.getConnectRequest();
                    socketWrapper.sendMessage(connectRequest);
                    String response = socketWrapper.readMessage();
                    String playerName = response.substring(response.indexOf(' ') + 1);
                    Platform.runLater(() -> onConnect.accept(playerName));
                } catch (IOException e) {
                    Platform.runLater(onFailConnection);
                }
                return null;
            }
        }).start();
    }

    public void makeMove(int row, int column, Runnable onSuccess, Runnable onLostConnection) {
        String turnRequest = MessagesProcessor.getTurnRequest(row, column);
        socketWrapper.sendMessage(turnRequest);
        try {
            String response = socketWrapper.readMessage();
            if ("success".equals(response)) {
                onSuccess.run();
            }
        } catch (IOException e) {
            onLostConnection.run();
        }
    }

    public void waitOpponentTurn(BiConsumer<Integer, Integer> onOpponentTurn) {
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
