package com.rprtr258.network;

import java.io.*;
import java.net.Socket;

public class SocketWrapper {
    private BufferedReader in = null;
    private PrintWriter out = null;

    public SocketWrapper(Socket socket) {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        } catch (IOException e) {
            // TODO: think
            e.printStackTrace();
        }
    }

    // TODO: remove debug output(not only in this class)
    public void sendMessage(String message) {
        out.println(message);
        System.out.printf("Sent \"%s\"\n", message);
    }

    public String readMessage() throws IOException {
        String message = readToken();
        System.out.printf("Received \"%s\"\n", message);
        return message;
    }

    private String readToken() throws IOException {
        return in.readLine();
    }
}
