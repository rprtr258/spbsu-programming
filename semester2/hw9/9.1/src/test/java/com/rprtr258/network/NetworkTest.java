package com.rprtr258.network;

import com.rprtr258.server.Server;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NetworkTest {
    private Client firstClient = null;
    private Client secondClient = null;

    @Before
    public void runServer() throws InterruptedException {
        Thread serverThread = new Thread(() -> Server.main(null));
        serverThread.start();
        Thread.sleep(1000);
        firstClient = new Client();
        secondClient = new Client();
        firstClient.tryConnectServer("localhost", 12345, (s) -> assertEquals("X", s), Assert::fail);
        Thread.sleep(1000);
        secondClient.tryConnectServer("localhost", 12345, (s) -> assertEquals("O", s), Assert::fail);
        Thread.sleep(1000);
        firstClient.configure(() -> {}, () -> {});
        secondClient.configure(() -> {}, () -> {});
    }

    @After
    public void stopServer() {
        firstClient.disconnect();
        secondClient.disconnect();
    }

    @Test
    public void firstWinTest() throws InterruptedException {
        /*
          XXX
          OO.
          ...
        */
        playGame(new int[][]{{0, 0}, {1, 0},
                             {0, 1}, {1, 1},
                             {0, 2}},
                "X");
    }

    @Test
    public void secondWinTest() throws InterruptedException {
        /*
          OX.
          XOX
          ..O
        */
        playGame(new int[][]{{1, 0}, {0, 0},
                             {0, 1}, {1, 1},
                             {1, 2}, {2, 2}},
                "O");
    }


    @Test
    public void drawTest() throws InterruptedException {
        /*
          OXX
          XOO
          XOX
        */
        playGame(new int[][]{{0, 1}, {0, 0},
                             {0, 2}, {1, 1},
                             {1, 0}, {1, 2},
                             {2, 0}, {2, 1},
                             {2, 2}},
                "draw");
    }

    @Test
    public void restartTest() throws InterruptedException {
        playGame(new int[][]{{0, 1}, {0, 0},
                        {0, 2}, {1, 1},
                        {1, 0}, {1, 2},
                        {2, 0}, {2, 1},
                        {2, 2}},
                "draw");
        firstClient.restart((s) -> assertEquals("O", s));
        secondClient.restart((s) -> assertEquals("X", s));
    }

    private void playGame(int turns[][], String expectedResult) throws InterruptedException {
        secondClient.waitGameChanges((r, c) -> {
            assertTrue(turns[0][0] == r);
            assertTrue(turns[0][1] == c);
        }, (s) -> fail());
        for (int i = 0; i < turns.length; i++) {
            Thread.sleep(500);
            int row = turns[i][0];
            int column = turns[i][1];
            Client currentClient = (i % 2 == 0 ? firstClient : secondClient);
            if (i < turns.length - 1) {
                makeMoveWithoutEndingGame(currentClient, row, column);
                if (i < turns.length - 2) {
                    waitOpponentTurn(currentClient, turns[i + 1][0], turns[i + 1][1]);
                } else {
                    waitGameEnd(currentClient, turns[i + 1][0], turns[i + 1][1], expectedResult);
                }
            } else {
                makeMoveEndingGame(currentClient, row, column, expectedResult);
            }
        }
    }

    private void waitOpponentTurn(Client playerClient, int expectedI, int expectedJ) {
        playerClient.waitGameChanges((i, j) -> {
            assertTrue(expectedI == i);
            assertTrue(expectedJ == j);
        }, (s) -> fail());
    }

    private void waitGameEnd(Client playerClient, int expectedI, int expectedJ, String expectedResult) {
        playerClient.waitGameChanges((i, j) -> {
            assertTrue(expectedI == i);
            assertTrue(expectedJ == j);
        }, (s) -> assertEquals(expectedResult, s));
    }

    private void makeMoveWithoutEndingGame(Client playerClient, int i, int j) {
        playerClient.makeMove(i, j, () -> {}, () -> {}, (s) -> fail());
    }

    private void makeMoveEndingGame(Client playerClient, int i, int j, String expectedResult) {
        playerClient.makeMove(i, j, () -> {}, Assert::fail, (s) -> assertEquals(expectedResult, s));
    }
}