package com.rprtr258;

import com.rprtr258.hashtable.*;

import java.util.Scanner;

public class Input {
    static private Scanner in = new Scanner(System.in);

    public static CommandType inputCommand() {
        while (true) {
            Output.printString("> ");
            String command = in.nextLine();
            switch (command) {
                case "add":
                    return CommandType.addValue;
                case "remove":
                    return CommandType.removeValue;
                case "contains":
                    return CommandType.checkValue;
                case "info":
                    return CommandType.showInfo;
                case "print":
                    return CommandType.print;
                case "fill":
                    return CommandType.checkoutFile;
                case "hash":
                    return CommandType.changeHash;
                case "quit":
                    return CommandType.close;
                case "help":
                    return CommandType.help;
                default:
                    Output.printString("Incorrect command. Write help to see list of commands.\n");
            }
        }
    }

    public static String inputValue() {
        Output.printString("> ");
        return in.nextLine();
    }

    public static String inputFilename() {
        Output.printString("> ");
        String filename = in.nextLine();
        while (!filename.matches("\\w+(\\.\\w*)*")) {
            Output.printlnString("Incorrect filename");
            filename = in.nextLine();
        }
        return filename;
    }

    public static boolean inputChoice() {
        Output.printString("> ");
        String choice = in.nextLine();
        return "y".equals(choice);
    }

    public static HashStrategy inputHashStrategy() {
        Output.printString("> ");
        int newBound = Integer.valueOf(in.nextLine());
        return new CharSumHash(newBound);
    }

    public static void setIn(Scanner newIn) {
        in = newIn;
    }
}
