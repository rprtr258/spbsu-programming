package com.rprtr258.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket socket = new ServerSocket(12345)) {
            while (true) {
                Socket clientSocket = socket.accept();
                ClientWorker worker = new ClientWorker(clientSocket);
                Thread thread = new Thread(worker);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
