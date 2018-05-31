package com.rprtr258.network;

import java.io.IOException;
import java.net.Socket;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Class that can be used to make application connecting Tic-Tac-Toe server and perform actions.
 */
public class Client {
    private SocketWrapper socketWrapper = null;
    private Runnable onLostConnection = null;
    private Runnable onDisconnect;

    /**
     * Configures reactions to events.
     *
     * @param onLostConnection reaction to lost server connection event.
     * @param onDisconnect reaction to client disconnect event.
     */
    public void configure(Runnable onLostConnection, Runnable onDisconnect) {
        this.onLostConnection = onLostConnection;
        this.onDisconnect = onDisconnect;
    }

    /**
     * Tries connect given server.
     *
     * @param host server host.
     * @param port server port.
     * @param onConnect reaction to connect event.
     * @param onFailConnection reaction to fail connection event.
     */
    public void tryConnectServer(String host, int port, Consumer<String> onConnect, Runnable onFailConnection) {
        new Thread(() -> {
            String connectRequest = MessagesProcessor.getConnectRequest();
            try {
                socketWrapper = new SocketWrapper(new Socket(host, port));
                socketWrapper.sendMessage(connectRequest);
                waitConnected(onConnect);
            } catch (IOException e) {
                e.printStackTrace();
                onFailConnection.run();
            }
        }).start();
    }

    /**
     * Makes turn in given position.
     *
     * @param row row coordinate of cell.
     * @param column column coordinate of cell.
     * @param onSuccess reaction to success turn.
     * @param onGameContinue reaction to game continuing.
     * @param onGameEnd reaction to game end.
     */
    public void makeMove(int row, int column, Runnable onSuccess, Runnable onGameContinue, Consumer<String> onGameEnd) {
        String turnRequest = MessagesProcessor.getTurnRequest(row, column);
        socketWrapper.sendMessage(turnRequest);
        try {
            String response = socketWrapper.readMessage();
            if ("disconnect".equals(response)) {
                onDisconnect.run();
                return;
            }
            if ("success".equals(response)) {
                onSuccess.run();
            }
            String gameState = socketWrapper.readMessage();
            if (gameState.startsWith("win")) {
                String winner = gameState.substring(gameState.indexOf(' ') + 1);
                onGameEnd.accept(winner);
            } else if ("draw".equals(gameState)) {
                onGameEnd.accept("draw");
            } else {
                onGameContinue.run();
            }
        } catch (IOException e) {
            onLostConnection.run();
        }
    }

    /**
     * Waits for opponent turn.
     *
     * @param onOpponentTurn reaction to opponent turn.
     * @param onGameEnd reaction to game end.
     */
    public void waitGameChanges(BiConsumer<Integer, Integer> onOpponentTurn, Consumer<String> onGameEnd) {
        new Thread(() -> {
            try {
                String opponentTurn = socketWrapper.readMessage();
                if ("disconnect echo".equals(opponentTurn)) {
                    onDisconnect.run();
                    return;
                }
                if (opponentTurn.matches(MessagesProcessor.OPPONENT_TURN_REGEXP)) {
                    int i = MessagesProcessor.parseRow(opponentTurn);
                    int j = MessagesProcessor.parseColumn(opponentTurn);
                    onOpponentTurn.accept(i, j);
                }
                String gameState = socketWrapper.readMessage();
                if (gameState.startsWith("win")) {
                    onGameEnd.accept(gameState.substring(gameState.indexOf(' ') + 1));
                } else if ("draw".equals(gameState)) {
                    onGameEnd.accept("draw");
                }
            } catch (IOException e) {
                onLostConnection.run();
            }
        }).start();
    }

    /**
     * Restarts game.
     *
     * @param onGameBegin reaction to new game begin.
     */
    public void restart(Consumer<String> onGameBegin) {
        new Thread(() -> {
            String restartRequest = MessagesProcessor.getRestartRequest();
            socketWrapper.sendMessage(restartRequest);
            try {
                waitConnected(onGameBegin);
            } catch (IOException e) {
                onLostConnection.run();
            }
        }).start();
    }

    /**
     * Disconnects client form server.
     */
    public void disconnect() {
        socketWrapper.sendMessage("disconnect");
        socketWrapper.disconnect();
    }

    private void waitConnected(Consumer<String> onConnect) throws IOException {
        String response = socketWrapper.readMessage();
        String playerName = response.substring(response.indexOf(' ') + 1);
        String connectedMessage = "";
        while (!MessagesProcessor.getConnectRequest().equals(connectedMessage)) {
            connectedMessage = socketWrapper.readMessage();
        }
        onConnect.accept(playerName);
    }
}
