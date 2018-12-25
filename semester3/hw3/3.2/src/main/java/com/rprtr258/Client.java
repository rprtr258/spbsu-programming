package com.rprtr258;

import javafx.application.*;
import javafx.geometry.Point2D;

import java.io.*;
import java.net.*;

/**
 * Client class
 */
public class Client {
    public static void main(String[] args) {
        System.out.println("Connecting to host: " + args[0]);
        System.out.println("Connecting to port: " + args[1]);
        try {
            Socket socketChannel = new Socket();
            socketChannel.connect(new InetSocketAddress("127.0.0.1", 1337));
            MainWindow.setSocket(socketChannel);
            MainWindow.setCoords(new Point2D(200, 100), new Point2D(600, 100));
        } catch (IOException e) {
            System.err.println("Error occurred during connection:");
            e.printStackTrace();
            Platform.exit();
        }
        Application.launch(MainWindow.class, args);
    }
}