package com.rprtr258.server;

import java.io.*;
import java.net.Socket;

public class ClientWorker implements Runnable {
    private InputStream in = null;
    private OutputStream out = null;
    private String clientName = null;

    public ClientWorker(Socket socket) {
        try {
            in = socket.getInputStream();
            out = socket.getOutputStream();
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

    private void sendMessage(String message) throws IOException {
        out.write((message + "\n").getBytes());
        out.flush();
        System.out.printf("Server: Sent \"%s\" to client \"%s\"\n", message, clientName);
    }

    private String readMessage() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String result = br.readLine();
        System.out.printf("Server: Received \"%s\" from client \"%s\"\n", result, clientName);
        return result;
    }
}
