package com.rprtr258;

import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

public class MainTest {
    private static InputStream systemIn = System.in;
    private static PrintStream systemOut = System.out;
    private static ByteArrayInputStream myIn = null;
    private static ByteArrayOutputStream myBAOS = null;
    private static PrintStream myOut = null;

    @Test
    public void addTest() throws IOException {
        String input = "add\n" +
            "Kappa\n" +
            "add\n" +
            "Keepo\n" +
            "add\n" +
            "Kappa\n" +
            "print\n" +
            "quit\n";
        String expected = "Write value to add:\n" +
            "Write value to add:\n" +
            "Write value to add:\n" +
            "Hashtable of size 10000:\n" +
            "493: Kappa\n" +
            "500: Keepo\n";
        inputOutputCheck(input, expected);
    }

    @Test
    public void infoTest() throws IOException {
        String input = "add\nKappa\nadd\nKeepo\nadd\nKappa\ninfo\nquit\n";
        String expected = "Write value to add:\n" +
            "Write value to add:\n" +
            "Write value to add:\n" +
            "Cells: 2\n" +
            "Load factor: 0,000200\n" +
            "Conflicts: 0\n" +
            "Max chain length: 1\n";
        inputOutputCheck(input, expected);
    }

    @Test
    public void changeHashTest() throws IOException {
        String input = "add\n" +
            "Kappa\n" +
            "add\n" +
            "Keepo\n" +
            "add\n" +
            "KappaRoss\n" +
            "hash\n" +
            "77\n" +
            "print\n" +
            "quit\n";
        String expected = "Write value to add:\n" +
            "Write value to add:\n" +
            "Write value to add:\n" +
            "Write number of cells:\n" +
            "Hashtable of size 77:\n" +
            "31: Kappa\n" +
            "38: Keepo\n" +
            "69: KappaRoss\n";
        inputOutputCheck(input, expected);
    }

    @Test
    public void fileAddTest() throws IOException {
        File file = new File("file.txt");
        file.createNewFile();
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write("Kappa\n" +
            "Keepo\n" +
            "KappaClaus");
        fileWriter.close();
        String input = "add\n" +
            "ResidentSleeper\n" +
            "fill\n" +
            "file.txt\n" +
            "y\n" +
            "print\n" +
            "quit\n";
        String expected = "Write value to add:\n" +
            "Write name of file to checkout:\n" +
            "Reset hashtable(y/n)):\n" +
            "Hashtable of size 10000:\n" +
            "493: Kappa\n" +
            "500: Keepo\n" +
            "997: KappaClaus\n" +
            "1550: ResidentSleeper\n";
        inputOutputCheck(input, expected);
        System.out.println(file.getAbsolutePath());
        file.delete();
    }

    @Test
    public void fileNotFoundTest() throws IOException {
        String input = "fill\n" +
            "file.txt\n" +
            "n\n" +
            "quit\n";
        String expected = "Write name of file to checkout:\n" +
            "Reset hashtable(y/n)):\n" +
            "File \"file.txt\" was not found.\n";
        inputOutputCheck(input, expected);
    }

    @Test
    public void fileRewriteTest() throws IOException {
        File file = new File("file.txt");
        file.createNewFile();
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write("Kappa\n" +
            "Keepo\n" +
            "KappaClaus");
        fileWriter.close();
        String input = "add\n" +
            "ResidentSleeper\n" +
            "fill\n" +
            "file.txt\n" +
            "n\n" +
            "print\n" +
            "quit\n";
        String expected = "Write value to add:\n" +
            "Write name of file to checkout:\n" +
            "Reset hashtable(y/n)):\n" +
            "Hashtable of size 10000:\n" +
            "493: Kappa\n" +
            "500: Keepo\n" +
            "997: KappaClaus\n";
        inputOutputCheck(input, expected);
        file.delete();
    }

    @Test
    public void helpTest() throws IOException {
        String input = "help\nquit\n";
        String expected = "List of available commands:\n" +
            "add - add value to hashtable\n" +
            "remove - remove value to hashtable\n" +
            "info - get statistics of hashtable\n" +
            "print - print hashtable\n" +
            "fill - fill hashtable with values from file\n" +
            "hash - change hash function\n" +
            "quit - exit program\n" +
            "help - show this list\n";
        inputOutputCheck(input, expected);
    }

    @Test
    public void invalidCommandTest() throws IOException {
        String input = "lol\n" +
            "quit\n";
        String expected = "Incorrect command. Write help to see list of commands.\n";
        inputOutputCheck(input, expected);
    }

    @Test
    public void removeTest() throws IOException {
        String input = "add\n" +
            "Kappa\n" +
            "add\n" +
            "Keepo\n" +
            "add\n" +
            "Kappa\n" +
            "add\n" +
            "KappaRoss\n" +
            "remove\n" +
            "Kappa\n" +
            "print\n" +
            "quit\n";
        String expected = "Write value to add:\n" +
            "Write value to add:\n" +
            "Write value to add:\n" +
            "Write value to add:\n" +
            "Write value to remove:\n" +
            "Hashtable of size 10000:\n" +
            "500: Keepo\n" +
            "916: KappaRoss\n";
        inputOutputCheck(input, expected);
    }

    private static void inputOutputCheck(String input, String expectedOutput) throws IOException {
        makeInput(input);
        Main.main(null);
        String output = myBAOS.toString();
        assertEquals(expectedOutput, output);
        resetStreams();
    }

    private static void makeInput(String input) {
        myIn = new ByteArrayInputStream(input.getBytes());
        myBAOS = new ByteArrayOutputStream();
        myOut = new PrintStream(myBAOS);
        System.setIn(myIn);
        System.setOut(myOut);
    }

    private static void resetStreams() throws IOException {
        System.setIn(systemIn);
        System.setOut(systemOut);
        myIn.close();
        myBAOS.close();
        myOut.close();
    }
}