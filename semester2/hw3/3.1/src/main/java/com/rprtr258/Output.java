package com.rprtr258;

import java.io.*;

public class Output {
    private static PrintStream out = System.out;

    public static void printString(String string) {
        out.print(string);
    }

    public static void printlnString(String string) {
        printString(string);
        out.print("\n");
    }

    public static void printHelp() {
        out.print("List of available commands:\n");
        out.print("add - add value to hashtable\n");
        out.print("remove - remove value to hashtable\n");
        out.print("info - get statistics of hashtable\n");
        out.print("print - print hashtable\n");
        out.print("fill - fill hashtable with values from file\n");
        out.print("hash - change hash function\n");
        out.print("quit - exit program\n");
        out.print("help - show this list\n");
    }

    public static void setOut(PrintStream newOut) {
        out = newOut;
    }
}
