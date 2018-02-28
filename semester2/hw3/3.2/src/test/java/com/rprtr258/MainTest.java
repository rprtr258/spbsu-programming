package com.rprtr258;

import org.junit.Test;

import java.io.File;
import java.io.*;

import static org.junit.Assert.*;

public class MainTest {
    private static InputStream systemIn = System.in;
    private static PrintStream systemOut = System.out;
    private static ByteArrayInputStream myIn = null;
    private static ByteArrayOutputStream myOut = null;
    private static PrintStream myPSOut = null;

    @Test
    public void retypeFilenameTest() throws IOException {
        setStreams("lol..\nch6burek%\nkek\n");
        writeToFile("kek", "(* (+ 2 2) 2))");

        Main.main(null);
        String expected = "Write input filename or nothing to use \"file.txt\":\n" +
                "Incorrect file name. Please retype:\n" +
                "Incorrect file name. Please retype:\n" +
                "Prefix form: (* (+ 2 2) 2)\n" +
                "Infix form: (2 + 2) * 2\n" +
                "Result: 8\n";
        assertEquals(expected, myOut.toString());

        File file = new File("kek");
        file.delete();

        resetStreams();
    }

    @Test
    public void customTest() throws IOException {
        setStreams("kek\n");
        writeToFile("kek", "(* (+ 2 2) 2))");

        Main.main(null);
        String expected = "Write input filename or nothing to use \"file.txt\":\n" +
                "Prefix form: (* (+ 2 2) 2)\n" +
                "Infix form: (2 + 2) * 2\n" +
                "Result: 8\n";
        assertEquals(expected, myOut.toString());

        File file = new File("kek");
        file.delete();

        resetStreams();
    }

    @Test
    public void fileTXTTest() throws IOException {
        setStreams("\n");
        writeToFile("file.txt", "(* (+ 2 2) 2))");

        Main.main(null);
        String expected = "Write input filename or nothing to use \"file.txt\":\n" +
            "Prefix form: (* (+ 2 2) 2)\n" +
            "Infix form: (2 + 2) * 2\n" +
            "Result: 8\n";
        assertEquals(expected, myOut.toString());

        File file = new File("file.txt");
        file.delete();

        resetStreams();
    }

    @Test
    public void fileNotFoundTest() throws IOException {
        setStreams("\n");

        Main.main(null);
        String expected = "Write input filename or nothing to use \"file.txt\":\n" +
            "File not found.\n";
        assertEquals(expected, myOut.toString());

        resetStreams();
    }

    private static void setStreams(String input) {
        myIn = new ByteArrayInputStream(input.getBytes());
        myOut = new ByteArrayOutputStream();
        myPSOut = new PrintStream(myOut);
        System.setIn(myIn);
        System.setOut(myPSOut);
    }

    private static void writeToFile(String filename, String text) throws IOException {
        FileOutputStream fos = new FileOutputStream(filename);
        fos.write(text.getBytes());
        fos.close();
    }

    private void resetStreams() throws IOException {
        myIn.close();
        myPSOut.close();
        myOut.close();
    }
}