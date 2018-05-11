package com.rprtr258.network;

import java.io.*;
import java.net.Socket;

public class SocketWrapper {
    private Socket socket = null;
    private BufferedReader in = null;
    private PrintWriter out = null;

    public SocketWrapper(Socket socket) {
        this.socket = socket;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        out.println(message);
        System.out.printf("Sent \"%s\"\n", message);
    }

    public String readMessage() throws IOException {
        String result = in.readLine();
        System.out.printf("Received \"%s\"\n", result);
        return result;
    }
}
