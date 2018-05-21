package com.rprtr258.network;

import java.io.*;
import java.net.Socket;

public class SocketWrapper {
    private BufferedReader in = null;
    private PrintWriter out = null;
    private boolean debug = false;

    public SocketWrapper(Socket socket) {
        this(socket, false);
        debug = true;
    }

    public SocketWrapper(Socket socket, boolean debug) {
        this.debug = debug;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        } catch (IOException e) {
            // TODO: think
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        out.println(message);
        if (debug)
            System.out.printf("Sent \"%s\"\n", message);
    }

    public String readMessage() throws IOException {
        String message = readToken();
        if (debug)
            System.out.printf("Received \"%s\"\n", message);
        return message;
    }

    private String readToken() throws IOException {
        return in.readLine();
    }
}
