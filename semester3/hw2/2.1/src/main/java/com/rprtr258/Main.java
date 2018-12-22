package com.rprtr258;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.*;

public class Main {
    public static void main(String args[]) {
        Network network = loadNetwork("config");
        System.out.println(network);
        System.out.println(network.emulate(new RandomDiceRoller()));
    }

    private static Network loadNetwork(String file) {
        try {
            String config = new String(Files.readAllBytes(Paths.get(Main.class.getResource(file).toURI())));
            return NetworkFactory.createNetwork(config);
        } catch (IOException | URISyntaxException e) {
            System.out.println("Config file was not found.");
            return NetworkFactory.createNetwork("{\n" +
                                      "    type: os\n" +
                                      "    name: \"Microsoft\"\n" +
                                      "    security: 0\n" +
                                      "}\n" +
                                      "{\n" +
                                      "    type: user\n" +
                                      "    name: \"default\"\n" +
                                      "    neighbours: []\n" +
                                      "    os: \"Windows\"\n" +
                                      "    infected: true\n" +
                                      "}");
        }
    }
}
