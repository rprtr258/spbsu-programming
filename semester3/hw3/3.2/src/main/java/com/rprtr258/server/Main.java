package com.rprtr258.server;

import java.io.IOException;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.Random;

public class Main {
    private static Random generator = new Random();

    public static void main(String[] args) {
        try {
            int port = getPort();
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(port));
            System.out.println("Host: \"" + getIpAddress() + "\"");
            System.out.println("Port: \"" + port + "\"");
            SocketChannel client = serverSocketChannel.accept();
            System.out.println("Connection accepted from " + client.getRemoteAddress());
            client.configureBlocking(false);
            ByteBuffer buffer = ByteBuffer.allocate(7);
            for (byte c : "#667799".getBytes())
                buffer.put(c);
            buffer.flip();
            client.write(buffer);
            buffer.clear();
        } catch (IOException e) {
            System.err.println("Error occured:");
            e.printStackTrace();
        }
    }

    private static String getIpAddress() throws IOException {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("google.com", 80));
        String result = socket.getLocalAddress().toString().substring(1);
        socket.close();
        return result;
    }

    private static int getPort() {
        return 1337;
        //return generator.nextInt(30000) + 10000;
    }
}
