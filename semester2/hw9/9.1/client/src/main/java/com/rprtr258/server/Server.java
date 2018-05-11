package com.rprtr258.server;

import com.rprtr258.game.TicTacToe;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int MAX_PLAYERS = 2;

    public static void main(String[] args) {
        try (ServerSocket socket = new ServerSocket(12345)) {
            Thread playerThreads[] = {null, null};
            TicTacToe game = new TicTacToe();
            for (int player = 0; player < MAX_PLAYERS; player++) {
                Socket clientSocket = socket.accept();
                ClientWorker worker = new ClientWorker(clientSocket, player + 1, game);
                playerThreads[player] = new Thread(worker);
            }
            for (Thread playerThread : playerThreads) {
                playerThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
