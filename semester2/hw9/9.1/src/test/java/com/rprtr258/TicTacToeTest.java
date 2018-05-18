package com.rprtr258;

import com.rprtr258.game.GameState;
import com.rprtr258.game.TicTacToe;
import org.junit.Test;

import static org.junit.Assert.*;

public class TicTacToeTest {
    @Test
    public void playerChangeTest() {
        TicTacToe game = new TicTacToe();
        assertEquals(GameState.CROSS_TURN, game.getState());
        assertTrue(game.makeTurn(0, 0)); // X
        assertEquals(GameState.ZERO_TURN, game.getState());
        assertTrue(game.makeTurn(0, 1)); // O
        assertEquals(GameState.CROSS_TURN, game.getState());
    }

    @Test
    public void crossWinTest() {
        /* XXX
         * OO.
         * ...
         */
        TicTacToe game = new TicTacToe();
        assertTrue(game.makeTurn(0, 0)); // X
        assertTrue(game.makeTurn(1, 0)); // O
        assertTrue(game.makeTurn(0, 1)); // X
        assertTrue(game.makeTurn(1, 1)); // O
        assertTrue(game.makeTurn(0, 2)); // X
        assertEquals(GameState.CROSS_WIN, game.getState());
    }

    @Test
    public void zeroWinTest() {
        /* .XX
         * OOO
         * X..
         */
        TicTacToe game = new TicTacToe();
        assertTrue(game.makeTurn(2, 0)); // X
        assertTrue(game.makeTurn(1, 0)); // O
        assertTrue(game.makeTurn(0, 1)); // X
        assertTrue(game.makeTurn(1, 1)); // O
        assertTrue(game.makeTurn(0, 2)); // X
        assertTrue(game.makeTurn(1, 2)); // O
        assertEquals(GameState.ZERO_WIN, game.getState());
    }

    @Test
    public void drawTest() {
        /* XOX
         * XOO
         * OXX
         */
        TicTacToe game = new TicTacToe();
        assertTrue(game.makeTurn(0, 0)); // X
        assertTrue(game.makeTurn(0, 1)); // O
        assertTrue(game.makeTurn(0, 2)); // X
        assertTrue(game.makeTurn(1, 1)); // O
        assertTrue(game.makeTurn(1, 0)); // X
        assertTrue(game.makeTurn(1, 2)); // O
        assertTrue(game.makeTurn(2, 1)); // X
        assertTrue(game.makeTurn(2, 0)); // O
        assertTrue(game.makeTurn(2, 2)); // X
        assertEquals(GameState.DRAW, game.getState());
    }

    @Test
    public void turnAfterEndTest() {
        /* XXX
         * OO.
         * ...
         */
        TicTacToe game = new TicTacToe();
        assertTrue(game.makeTurn(0, 0)); // X
        assertTrue(game.makeTurn(1, 0)); // O
        assertTrue(game.makeTurn(0, 1)); // X
        assertTrue(game.makeTurn(1, 1)); // O
        assertTrue(game.makeTurn(0, 2)); // X
        assertEquals(GameState.CROSS_WIN, game.getState());
        assertFalse(game.canMakeTurn("O", 1, 2));
        assertFalse(game.makeTurn(1, 2)); // O
    }

    @Test
    public void playerChangeAfterRestartTest() {
        /* XXX
         * OO.
         * ...
         */
        TicTacToe game = new TicTacToe();
        assertTrue(game.makeTurn(0, 0)); // X
        assertTrue(game.makeTurn(1, 0)); // O
        assertTrue(game.makeTurn(0, 1)); // X
        assertTrue(game.makeTurn(1, 1)); // O
        assertTrue(game.makeTurn(0, 2)); // X
        game.restart();
        assertEquals(GameState.CROSS_TURN, game.getState());
    }
}