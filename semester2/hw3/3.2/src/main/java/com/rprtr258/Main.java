package com.rprtr258;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String filename = inputFilename();
        if ("".equals(filename))
            filename = "file.txt";
        String treeString = null;
        try {
            FileReader fr = new FileReader(filename);
            Scanner fileScanner = new Scanner(fr);
            treeString = fileScanner.nextLine();
            fr.close();
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.print("File not found.\n");
            return;
        } catch (IOException e) {
            e.printStackTrace();
        }
        ExpressionTree tree = new ExpressionTree(treeString);
        System.out.printf("Prefix form: %s\n", tree);
        System.out.printf("Infix form: %s\n", tree.infixForm());
        int result = tree.calc();
        System.out.printf("Result: %d\n", result);
    }

    static private String inputFilename() {
        System.out.print("Write input filename or nothing to use \"file.txt\":\n");
        Scanner in = new Scanner(System.in);
        String result = in.nextLine();
        while (!result.matches("\\w*(\\.\\w+)?")) {
            System.out.print("Incorrect file name. Please retype:\n");
            result = in.nextLine();
        }
        return result;
    }
}
