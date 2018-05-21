package com.rprtr258.server;

import com.rprtr258.game.TicTacToe;
import com.rprtr258.network.SocketWrapper;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    private static final int MAX_PLAYERS = 2;
    private static final String playerNames[] = {"X", "O"};

    public static void main(String[] args) {
        // TODO: change order: first receive "connect", then reply with "player %s"
        // TODO: waiting other client to connect
        try (ServerSocket socket = new ServerSocket(12345)) {
            Thread playerThreads[] = {null, null};
            TicTacToe game = new TicTacToe();
            ServerWorker serverWorker = new ServerWorker();
            for (int i = 0; i < MAX_PLAYERS; i++) {
                SocketWrapper playerSocket = new SocketWrapper(socket.accept());
                String playerName = playerNames[i];
                serverWorker.addClient(playerName, playerSocket);
                ClientWorker worker = new ClientWorker(playerSocket, playerName, game, serverWorker);
                playerThreads[i] = new Thread(worker);
            }
            for (Thread playerThread : playerThreads) {
                playerThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
