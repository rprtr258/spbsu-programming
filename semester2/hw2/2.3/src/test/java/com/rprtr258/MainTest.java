package com.rprtr258;

import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

public class MainTest {
    private static InputStream systemIn = System.in;
    private static PrintStream systemOut = System.out;
    private static InputStream myIn = null;
    private static PrintStream myOut = null;
    private static ByteArrayOutputStream baos = null;

    @Test
    public void incorrectInputTest() {
        String input = "/*-+-/ 67 7 6r   78\n+22\n+2 2\n"; // ((1 / 2) * 3 - 4) + 5 = 1
        setSystemStreams(input);

        Main.main(null);
        String expectedOutput = "Write expression in prefix form to calc, unary minus is not supported(e.g. +2 2):\n" +
                                "Incorrect expression. Please retype:\n" +
                                "Incorrect expression. Please retype:\n" +
                                "Result is: 4\n";
        assertEquals(expectedOutput, baos.toString());
        closeMyStreams();
    }

    @Test
    public void correctInputTest() {
        String input = "+ - * / 1 2 3 4 5\n"; // ((1 / 2) * 3 - 4) + 5 = 1
        setSystemStreams(input);

        Main.main(null);
        String expectedOutput = "Write expression in prefix form to calc, unary minus is not supported(e.g. +2 2):\nResult is: 1\n";
        assertEquals(expectedOutput, baos.toString());

        closeMyStreams();
    }

    private void setSystemStreams(String input) {
        myIn = new ByteArrayInputStream(input.getBytes());
        baos = new ByteArrayOutputStream();
        myOut = new PrintStream(baos);
        System.setIn(myIn);
        System.setOut(myOut);
    }

    private void closeMyStreams() {
        System.setIn(systemIn);
        System.setOut(systemOut);
        try {
            baos.close();
            myIn.close();
            myOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}