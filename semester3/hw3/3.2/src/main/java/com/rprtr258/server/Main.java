package com.rprtr258.server;

import java.io.IOException;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class Main {
    private static Random generator = new Random();
    private static ByteBuffer buffer = ByteBuffer.allocate(1024);

    public static void main(String[] args) {
        try {
            int port = getPort();
            Selector selector = Selector.open();
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(port));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, serverSocketChannel.validOps());
            System.out.println("Host: \"" + getIpAddress() + "\"");
            System.out.println("Port: \"" + port + "\"");
            boolean written = false;
            while (true) {
                selector.select();
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = keys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isAcceptable()) {
                        SocketChannel clientChannel = serverSocketChannel.accept();
                        System.out.println("Connection accepted from " + clientChannel.getRemoteAddress());
                        clientChannel.configureBlocking(false);
                        clientChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                    } else if (key.isReadable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        ByteBuffer buf = ByteBuffer.allocate(256);
                        client.read(buf);
                        String result = new String(buf.array()).trim();
                        System.out.println(result);
                    } else if (key.isWritable()) {
                        if (!written) {
                            written = true;
                            SocketChannel client = (SocketChannel) key.channel();
                            client.write(ByteBuffer.wrap("#667799".getBytes()));
                            System.out.println("Sent \"#667799\" to \"user1\"");
                            client.write(ByteBuffer.wrap("user1".getBytes()));
                            System.out.println("Sent \"user1\" to \"user1\"");
                        }
                    }
                    iterator.remove();
                }
            }
        } catch (IOException e) {
            System.err.println("Error occurred:");
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
