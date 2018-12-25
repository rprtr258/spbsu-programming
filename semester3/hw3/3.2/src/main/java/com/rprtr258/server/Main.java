package com.rprtr258.server;

import com.rprtr258.MainWindow;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Point2D;

import java.io.*;
import java.net.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Arrays.stream(args).forEach(System.out::println);
        int port = getPort();
        try {
            ServerSocket serverSocketChannel = new ServerSocket(port);
            System.out.println("Host: \"" + getIpAddress() + "\"");
            System.out.println("Port: \"" + port + "\"");
            Socket client = serverSocketChannel.accept();
            System.out.println("Connection accepted from " + client.getRemoteSocketAddress());
            MainWindow.setSocket(client);
            MainWindow.setCoords(new Point2D(600, 100), new Point2D(200, 100));
       } catch (IOException e) {
           System.err.println("Error occurred:");
           e.printStackTrace();
           Platform.exit();
       }
       Application.launch(MainWindow.class, args);
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
        // TODO: random port generating
        //return generator.nextInt(30000) + 10000;
    }
}
