package com.rprtr258;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class SocketAdapter {
    private Scanner in = null;
    private PrintWriter out = null;
    private InputStream is = null;

    public SocketAdapter(Socket socket) {
        try {
            is = socket.getInputStream();
            in = new Scanner(is);
            out = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(String message) {
        out.write(message);
        out.flush();
    }

    public boolean hasNext() {
        try {
            return is.available() > 0;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String nextLine() {
        return in.nextLine();
    }
}
