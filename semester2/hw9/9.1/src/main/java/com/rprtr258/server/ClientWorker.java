package com.rprtr258.server;

import com.rprtr258.game.GameState;
import com.rprtr258.game.TicTacToe;
import com.rprtr258.network.MessagesProcessor;

import java.io.IOException;

public class ClientWorker implements Runnable {
    private String clientName = null;
    private String opponentSocketId = null;
    private String socketId = null;
    private TicTacToe game = null;
    private ServerWorker serverWorker = null;

    public ClientWorker(String playerName, TicTacToe game, ServerWorker serverWorker) {
        this.clientName = playerName;
        this.socketId = playerName;
        this.opponentSocketId = ("X".equals(playerName) ? "O" : "X");
        this.game = game;
        this.serverWorker = serverWorker;
    }

    @Override
    public void run() {
        serverWorker.sendTo(clientName, "connected");
        try {
            while (true) {
                String message = serverWorker.readMessage(socketId);
                if (message.matches(MessagesProcessor.MY_TURN_REGEXP)) {
                    int row = Integer.parseInt(message.substring(message.indexOf(' ') + 1, message.lastIndexOf(' ')));
                    int column = Integer.parseInt(message.substring(message.lastIndexOf(' ') + 1));
                    if (game.canMakeTurn(clientName, row, column)) {
                        game.makeTurn(row, column);
                        serverWorker.sendTo(socketId, "success");
                        serverWorker.sendTo(opponentSocketId, "op" + message);
                    } else {
                        serverWorker.sendTo(socketId, "incorrect turn");
                    }
                    GameState gameState = game.getState();
                    String gameStateString = MessagesProcessor.getGameStateMessage(gameState);
                    serverWorker.sendAll(gameStateString);
                    System.out.println(game);
                } else if ("restart".equals(message)) {
                    clientName = ("X".equals(clientName) ? "O" : "X");
                    serverWorker.queueTo(socketId, "player " + clientName);
                    serverWorker.connectConfirm(clientName);
                    game.restart();
                }
            }
        } catch (IOException e) {
            // TODO: stop server in case of client disconnect
            System.out.println("Client " + clientName + " disconnected");
            e.printStackTrace();
        }
    }
}
