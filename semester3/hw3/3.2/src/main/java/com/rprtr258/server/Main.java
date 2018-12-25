package com.rprtr258.server;

import com.rprtr258.client.*;
import javafx.geometry.Point2D;

import java.awt.*;
import java.io.IOException;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.*;
import java.util.List;

public class Main {
    private static Random generator = new Random();
    private static Map<String, Tank> tanks = new TreeMap<>();

    public static void main(String[] args) {
        List<Point2D> pointsList = Arrays.asList(
            new Point2D(21, 217),
            new Point2D(151, 217),
            new Point2D(293, 315),
            new Point2D(317, 386),
            new Point2D(389, 444),
            new Point2D(408, 444),
            new Point2D(456, 402),
            new Point2D(464, 382),
            new Point2D(476, 370),
            new Point2D(489, 312),
            new Point2D(500, 303),
            new Point2D(621, 303),
            new Point2D(621, 456),
            new Point2D(21, 456)
        );
        Earth earth = new Earth(pointsList);
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
                            String initMessage =
                                "#667799\n" +
                                "user1\n";
                            initMessage += pointsList.size() + "\n";
                            for (Point2D p : pointsList)
                                initMessage += p.getX() + " " + p.getY() + "\n";
                            client.write(ByteBuffer.wrap(initMessage.getBytes()));
                            tanks.put("user1", new Tank(200, 100, "#667799", earth));
                            System.out.println("Sent init packet");
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
