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
    private Runnable onDisconnect;

    public void configure(Consumer<Exception> onLostConnection, Runnable onDisconnect) {
        this.onLostConnection = (e) -> Platform.runLater(() -> onLostConnection.accept(e));
        this.onDisconnect = () -> Platform.runLater(onDisconnect);
    }

    public void tryConnectServer(String host, int port, Consumer<String> onConnect, Runnable onFailConnection) {
        new Thread(new Task<Void>() {
            @Override
            protected Void call() {
                String connectRequest = MessagesProcessor.getConnectRequest();
                try {
                    socketWrapper = new SocketWrapper(new Socket(host, port));
                    socketWrapper.sendMessage(connectRequest);
                    waitConnected(onConnect);
                } catch (IOException e) {
                    Platform.runLater(onFailConnection);
                }
                return null;
            }
        }).start();
    }

    // TODO: message reading and proceeding through polymorphism to handle "disconnect" everywhere(?)
    public void makeMove(int row, int column, Runnable onSuccess, Runnable onGameContinue, Consumer<String> onGameEnd) {
        String turnRequest = MessagesProcessor.getTurnRequest(row, column);
        socketWrapper.sendMessage(turnRequest);
        try {
            String response = socketWrapper.readMessage();
            if ("disconnect".equals(response)) {
                onDisconnect.run();
            }
            if ("success".equals(response)) {
                Platform.runLater(onSuccess);
            }
            String gameState = socketWrapper.readMessage();
            if (gameState.startsWith("win")) {
                Platform.runLater(() -> onGameEnd.accept(gameState.substring(gameState.indexOf(' ') + 1)));
            } else if ("draw".equals(gameState)) {
                Platform.runLater(() -> onGameEnd.accept("draw"));
            } else {
                Platform.runLater(onGameContinue);
            }
        } catch (IOException e) {
            onLostConnection.accept(e);
        }
    }

    public void waitGameChanges(BiConsumer<Integer, Integer> onOpponentTurn, Consumer<String> onGameEnd) {
        new Thread(new Task<Void>() {
            @Override
            protected Void call() {
                try {
                    String opponentTurn = socketWrapper.readMessage();
                    if ("disconnect echo".equals(opponentTurn)) {
                        onDisconnect.run();
                        return null;
                    }
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
                    onLostConnection.accept(e);
                }
                return null;
            }
        }).start();
    }

    public void restart(Consumer<String> onGameBegin) {
        new Thread(new Task<Void>() {
            @Override
            protected Void call() {
                String connectRequest = MessagesProcessor.getRestartRequest();
                socketWrapper.sendMessage(connectRequest);
                try {
                    waitConnected(onGameBegin);
                } catch (IOException e) {
                    onLostConnection.accept(e);
                }
                return null;
            }
        }).start();
    }

    private void waitConnected(Consumer<String> onConnect) throws IOException {
        String response = socketWrapper.readMessage();
        String playerName = response.substring(response.indexOf(' ') + 1);
        String connectedMessage = "";
        while (!"connected".equals(connectedMessage)) {
            connectedMessage = socketWrapper.readMessage();
        }
        Platform.runLater(() -> onConnect.accept(playerName));
    }

    public void disconnect() {
        socketWrapper.sendMessage("disconnect");
        socketWrapper.disconnect();
    }

    public SocketWrapper getSocket() {
        return socketWrapper;
    }
}
