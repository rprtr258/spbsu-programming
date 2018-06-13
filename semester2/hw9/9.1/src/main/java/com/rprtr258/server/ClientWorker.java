package com.rprtr258.server;

import com.rprtr258.game.GameState;
import com.rprtr258.game.TicTacToe;
import com.rprtr258.network.MessagesProcessor;

import java.io.IOException;

/**
 * Client worker runnable which talks to clients during game.
 */
public class ClientWorker implements Runnable {
    private String clientName = null;
    private String opponentSocketId = null;
    private String socketId = null;
    private TicTacToe game = null;
    private ServerWorker serverWorker = null;
    private ServerState serverState = ServerState.RUNNING;

    /**
     * Constructs client worker.
     *
     * @param playerName player mark.
     * @param game game object reference.
     * @param serverWorker server worker.
     * @param serverState server state reference.
     */
    public ClientWorker(String playerName, TicTacToe game, ServerWorker serverWorker, ServerState serverState) {
        this.clientName = playerName;
        this.socketId = playerName;
        this.opponentSocketId = ("X".equals(playerName) ? "O" : "X");
        this.game = game;
        this.serverWorker = serverWorker;
        this.serverState = serverState;
    }

    /**
     * Run method.
     */
    @Override
    public void run() {
        serverWorker.sendTo(socketId, MessagesProcessor.CONNECT_REQUEST);
        try {
            while (serverState == ServerState.RUNNING) {
                String message = serverWorker.readMessage(socketId);
                if (message == null) {
                    throw new IOException("socket disconnected");
                } else if ("disconnect".equals(message)) {
                    serverWorker.sendAll("disconnect echo");
                    serverState = ServerState.STOPPED;
                } else if (message.matches(MessagesProcessor.MY_TURN_REGEXP)) {
                    int row = Integer.parseInt(message.substring(message.indexOf(' ') + 1, message.lastIndexOf(' ')));
                    int column = Integer.parseInt(message.substring(message.lastIndexOf(' ') + 1));
                    if (game.canMakeTurn(clientName, row, column)) {
                        game.makeTurn(row, column);
                        serverWorker.sendTo(socketId, "success");
                        serverWorker.sendTo(opponentSocketId, "op" + message);
                        GameState gameState = game.getState();
                        String gameStateString = MessagesProcessor.getGameStateMessage(gameState);
                        serverWorker.sendAll(gameStateString);
                    } else {
                        serverWorker.sendTo(socketId, "incorrect turn");
                    }
                } else if ("restart".equals(message)) {
                    clientName = ("X".equals(clientName) ? "O" : "X");
                    serverWorker.queueTo(socketId, "player " + clientName);
                    serverWorker.connectConfirm(socketId);
                    game.restart();
                }
            }
        } catch (IOException e) {
            serverState = ServerState.STOPPED;
            System.out.println("Client " + clientName + " disconnected");
        }
    }
}
