package com.rprtr258.network;

import javafx.application.Platform;
import javafx.concurrent.Task;

import java.io.IOException;
import java.net.Socket;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Client {
    private SocketWrapper socketWrapper = null;
    private Consumer<Exception> onLostConnection = null;

    public void setOnLostConnection(Consumer<Exception> onLostConnection) {
        this.onLostConnection = onLostConnection;
    }

    public void tryConnectServer(String host, int port, Consumer<String> onConnect, Runnable onFailConnection) {
        try {
            socketWrapper = new SocketWrapper(new Socket(host, port));
            String connectRequest = MessagesProcessor.getConnectRequest();
            socketWrapper.sendMessage(connectRequest);
            String response = socketWrapper.readMessage();
            String playerName = response.substring(response.indexOf(' ') + 1);
            String connectedMessage = socketWrapper.readMessage();
            if ("connected".equals(connectedMessage))
                Platform.runLater(() -> onConnect.accept(playerName));
        } catch (IOException e) {
            Platform.runLater(onFailConnection);
        }
    }

    public void makeMove(int row, int column, Runnable onSuccess, Consumer<Exception> onLostConnection, Consumer<String> onGameEnd) {
        String turnRequest = MessagesProcessor.getTurnRequest(row, column);
        socketWrapper.sendMessage(turnRequest);
        try {
            String response = socketWrapper.readMessage();
            if ("success".equals(response)) {
                Platform.runLater(onSuccess);
            }
            String gameState = socketWrapper.readMessage();
            if (gameState.startsWith("win")) {
                Platform.runLater(() -> onGameEnd.accept(gameState.substring(gameState.indexOf(' ') + 1)));
            } else if ("draw".equals(gameState)) {
                Platform.runLater(() -> onGameEnd.accept("draw"));
            }
        } catch (IOException e) {
            onLostConnection.accept(e);
        }
    }

    // TODO: divide waiting game result and waiting opponent turn
    public void waitGameChanges(BiConsumer<Integer, Integer> onOpponentTurn, Consumer<String> onGameEnd) {
        new Thread(new Task<Void>() {
            @Override
            protected Void call() {
                try {
                    String opponentTurn = socketWrapper.readMessage();
                    if (opponentTurn.matches(MessagesProcessor.OPPONENT_TURN_REGEXP)) {
                        int i = MessagesProcessor.parseRow(opponentTurn);
                        int j = MessagesProcessor.parseColumn(opponentTurn);
                        Platform.runLater(() -> onOpponentTurn.accept(i, j));
                    }
                    String gameState = socketWrapper.readMessage();
                    if (gameState.startsWith("win")) {
                        Platform.runLater(() -> onGameEnd.accept(gameState.substring(gameState.indexOf(' ') + 1)));
                    } else if ("draw".equals(gameState)) {
                        Platform.runLater(() -> onGameEnd.accept("draw"));
                    }
                } catch (IOException e) {
                    Platform.runLater(() -> onLostConnection.accept(e));
                }
                return null;
            }
        }).start();
    }
}
