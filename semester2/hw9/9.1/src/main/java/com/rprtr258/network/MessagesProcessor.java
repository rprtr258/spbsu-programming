package com.rprtr258.network;

public class MessagesProcessor {
    public final static String MY_TURN_REGEXP = "turn [0-2] [0-2]";
    public final static String OPPONENT_TURN_REGEXP = "opturn [0-2] [0-2]";

    public static int parseRow(String message) {
        return Integer.parseInt(message.substring(message.indexOf(' ') + 1, message.lastIndexOf(' ')));
    }

    public static int parseColumn(String message) {
        return Integer.parseInt(message.substring(message.lastIndexOf(' ') + 1));
    }
}
