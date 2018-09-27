package com.rprtr258.network;

import java.io.*;
import java.net.Socket;

/**
 * My wrapper of socket class.
 */
public class SocketWrapper {
    private BufferedReader in = null;
    private PrintWriter out = null;
    private boolean debug = false;

    /**
     * Makes wrapper of given socket.
     *
     * @param socket given socket.
     */
    public SocketWrapper(Socket socket) {
        this(socket, false);
    }

    /**
     * Makes wrapper of given socket with debug mode.
     *
     * @param socket given socket.
     * @param debug debug mode state.
     */
    public SocketWrapper(Socket socket, boolean debug) {
        this.debug = debug;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends message through socket.
     *
     * @param message message to send.
     */
    public void sendMessage(String message) {
        out.println(message);
        if (debug)
            System.out.printf("Sent \"%s\"\n", message);
    }

    /**
     * Receives message through socket.
     *
     * @return received message.
     * @throws IOException if occurred.
     */
    public String readMessage() throws IOException {
        String message = readToken();
        if (debug)
            System.out.printf("Received \"%s\"\n", message);
        return message;
    }

    /**
     * Disconnects socket.
     */
    public void disconnect() {
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.close();
    }

    private String readToken() throws IOException {
        return in.readLine();
    }
}
