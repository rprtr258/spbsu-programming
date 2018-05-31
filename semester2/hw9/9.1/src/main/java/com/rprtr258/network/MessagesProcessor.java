package com.rprtr258.network;

import com.rprtr258.game.GameState;

/**
 * Message processor of messages in game client-server protocol.
 */
public class MessagesProcessor {
    /**
     * Regex that checks if message is client's turn.
     */
    public final static String MY_TURN_REGEXP = "turn [0-2] [0-2]";
    /**
     * Regex that checks if message is opponent's turn.
     */
    public final static String OPPONENT_TURN_REGEXP = "opturn [0-2] [0-2]";
    /**
     * Connect request message.
     */
    public final static String CONNECT_REQUEST = "connect";

    /**
     * Makes game state message.
     *
     * @param gameState state of the game.
     * @return message encoding game state.
     */
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

    /**
     * Extracts row coordinate from turn message.
     *
     * @param message turn message.
     * @return row coordinate of turn.
     */
    public static int parseRow(String message) {
        return Integer.parseInt(message.substring(message.indexOf(' ') + 1, message.lastIndexOf(' ')));
    }

    /**
     * Extracts column coordinate from turn message.
     *
     * @param message turn message.
     * @return column coordinate of turn.
     */
    public static int parseColumn(String message) {
        return Integer.parseInt(message.substring(message.lastIndexOf(' ') + 1));
    }

    /**
     * Makes turn request.
     *
     * @param row row coordinate of cell.
     * @param column column coordinate of cell.
     * @return turn request message.
     */
    public static String getTurnRequest(int row, int column) {
        return String.format("turn %d %d", row, column);
    }

    /**
     * @return restart request message.
     */
    public static String getRestartRequest() {
        return "restart";
    }
}
