package com.rprtr258.network;

import com.rprtr258.game.GameState;

public class MessagesProcessor {
    public final static String MY_TURN_REGEXP = "turn [0-2] [0-2]";
    public final static String OPPONENT_TURN_REGEXP = "opturn [0-2] [0-2]";

    public static int parseRow(String message) {
        return Integer.parseInt(message.substring(message.indexOf(' ') + 1, message.lastIndexOf(' ')));
    }

    public static int parseColumn(String message) {
        return Integer.parseInt(message.substring(message.lastIndexOf(' ') + 1));
    }

    public static String getConnectRequest() {
        return "connect";
    }

    public static String getTurnRequest(int row, int column) {
        return String.format("turn %d %d", row, column);
    }

    public static String getGameStateMessage(GameState gameState) {
        switch (gameState) {
            case CROSS_WIN: {
                return "win X";
            }
            case ZERO_WIN: {
                return "win O";
            }
            case DRAW: {
                return "draw";
            }
            default: {
                return "game running";
            }
        }
    }

    public static String getRestartRequest() {
        return "restart";
    }
}
