package com.rprtr258.server;

import java.io.*;
import java.net.Socket;

public class ClientWorker implements Runnable {
    private BufferedReader in = null;
    private PrintWriter out = null;
    private String clientName = null;

    public ClientWorker(Socket socket) {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            clientName = readMessage();
            while (true) {
                String message = readMessage();
                if ("disconnect".equals(message))
                    break;
                sendMessage(String.format("Ok, %s, got %s", clientName, message));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(String message) {
        out.println(message);
        System.out.printf("Server: Sent \"%s\" to client \"%s\"\n", message, clientName);
    }

    private String readMessage() throws IOException {
        String result = in.readLine();
        System.out.printf("Server: Received \"%s\" from client \"%s\"\n", result, clientName);
        return result;
    }
}
