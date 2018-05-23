package com.rprtr258.server;

import com.rprtr258.game.TicTacToe;
import com.rprtr258.network.SocketWrapper;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Server main class.
 */
public class Server {
    private static final int MAX_PLAYERS = 2;
    private static final String playerNames[] = {"X", "O"};

    /**
     * Entry point. Processes clients and when all connected starts the game.
     *
     * @param args ignored.
     */
    public static void main(String[] args) {
        try (ServerSocket socket = new ServerSocket(12345)) {
            System.out.println("Server run on port 12345");
            Thread playerThreads[] = new Thread[MAX_PLAYERS];
            TicTacToe game = new TicTacToe();
            ServerWorker serverWorker = new ServerWorker();
            ServerState serverState = ServerState.RUNNING;
            for (int i = 0; i < MAX_PLAYERS; i++) {
                SocketWrapper playerSocket = new SocketWrapper(socket.accept(), true);
                System.out.println("player " + i + " connected");
                if ("connect".equals(playerSocket.readMessage())) {
                    String playerName = playerNames[i];
                    playerSocket.sendMessage("player " + playerName);
                    serverWorker.addClient(playerName, playerSocket);
                    ClientWorker worker = new ClientWorker(playerName, game, serverWorker, serverState);
                    playerThreads[i] = new Thread(worker);
                }
            }
            for (Thread playerThread : playerThreads) {
                playerThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
