package com.rprtr258;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * Socket adapter class
 */
public class SocketAdapter {
    private Scanner in = null;
    private PrintWriter out = null;
    private InputStream is = null;

    /**
     * SocketAdapter class constructor
     */
    public SocketAdapter(Socket socket) {
        try {
            is = socket.getInputStream();
            in = new Scanner(is);
            out = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes text to socket
     * @param message text to send
     */
    public void write(String message) {
        out.write(message);
        out.flush();
    }

    /**
     * @return true if has text to receive
     */
    public boolean hasNext() {
        try {
            return is.available() > 0;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @return read line of text
     */
    public String nextLine() {
        return in.nextLine();
    }
}
