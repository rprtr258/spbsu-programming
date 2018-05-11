package com.rprtr258.server;

import com.rprtr258.network.SocketWrapper;

import java.io.IOException;
import java.net.Socket;

public class ClientWorker implements Runnable {
    private SocketWrapper socketWrapper = null;
    private String clientName = null;

    public ClientWorker(Socket socket) {
        socketWrapper = new SocketWrapper(socket);
    }

    @Override
    public void run() {
        establishName();
        try {
            while (true) {
                String message = socketWrapper.readMessage();
                if ("disconnect".equals(message))
                    break;
                socketWrapper.sendMessage(String.format("Ok, %s, got %s", clientName, message));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void establishName() {
        try {
            String connectMessage = socketWrapper.readMessage();
            if (connectMessage.startsWith("connect "))
                clientName = connectMessage.substring(connectMessage.indexOf(' ') + 1);
            else
                ; // TODO: process incorrect client
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
