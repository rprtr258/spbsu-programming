package com.rprtr258;

import com.rprtr258.hashtable.*;

import java.util.Scanner;

public class InputReader {
    static private Scanner in = new Scanner(System.in);

    public static CommandType inputCommand() {
        while (true) {
            System.out.print("> ");
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
                    System.out.print("Incorrect command. Write help to see list of commands.\n");
            }
        }
    }

    public static String inputValue() {
        System.out.print("> ");
        return in.nextLine();
    }

    public static String inputFilename() {
        System.out.print("> ");
        String filename = in.nextLine();
        return filename;
    }

    public static boolean inputChoice() {
        System.out.print("> ");
        String choice = in.nextLine();
        return "y".equals(choice);
    }

    public static HashStrategy inputHashStrategy() {
        System.out.print("> ");
        int newBound = Integer.valueOf(in.nextLine());
        return new CharSumHash(newBound);
    }

    public static void setInput(Scanner newIn) {
        in = newIn;
    }
}
