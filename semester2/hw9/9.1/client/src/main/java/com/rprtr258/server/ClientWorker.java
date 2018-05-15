package com.rprtr258.server;

import com.rprtr258.game.TicTacToe;
import com.rprtr258.network.MessagesProcessor;
import com.rprtr258.network.SocketWrapper;

import java.io.IOException;
import java.net.Socket;

public class ClientWorker implements Runnable {
    private SocketWrapper socketWrapper = null;
    private SocketWrapper opponentSocketWrapper = null;
    private String clientName = null;
    private int playerNumber = 0;
    private TicTacToe game = null;
    private boolean myTurn;

    public ClientWorker(Socket socket, int playerNumber, TicTacToe game) {
        socketWrapper = new SocketWrapper(socket);
        this.playerNumber = playerNumber;
        clientName = (playerNumber == 1 ? "X" : "O");
        this.game = game;
    }

    @Override
    public void run() {
        sendConnectionInfo();
        try {
            while (true) {
                String message = socketWrapper.readMessage(String.format("player %s", clientName));
                if ("disconnect".equals(message))
                    // TODO: send it sometimes
                    break;
                if (message.matches(MessagesProcessor.MY_TURN_REGEXP)) {
                    int row = Integer.parseInt(message.substring(message.indexOf(' ') + 1, message.lastIndexOf(' ')));
                    int column = Integer.parseInt(message.substring(message.lastIndexOf(' ') + 1));
                    if (game.canMakeTurn(clientName, row, column)) {
                        game.makeTurn(row, column);
                        socketWrapper.sendMessage("success");
                        opponentSocketWrapper.sendMessage("op" + message);
                    } else {
                        // TODO: notify about win/loss/draw/incorrect turn
                        socketWrapper.sendMessage("not your turn");
                    }
                    System.out.println(game);
                }
            }
        } catch (IOException e) {
            // TODO: :C
            e.printStackTrace();
        }
    }

    public void setOpponentSocket(Socket opponentSocket) {
        opponentSocketWrapper = new SocketWrapper(opponentSocket);
    }

    private void sendConnectionInfo() {
        String mark = (playerNumber == 1 ? "X" : "O");
        socketWrapper.sendMessage(String.format("player %s", mark));
    }
}
