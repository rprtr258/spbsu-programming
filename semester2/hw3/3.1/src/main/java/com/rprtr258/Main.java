package com.rprtr258;

import com.rprtr258.hashtable.*;

import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        InputReader.setIn(new Scanner(System.in));
        Output.setOut(System.out);
        boolean isRunning = true;
        HashTable hashTable = new HashTable(new CharSumHash());
        while (isRunning) {
            CommandType command = InputReader.inputCommand();
            switch (command) {
                case addValue: {
                    Output.printString("Write value to add:");
                    String value = InputReader.inputValue();
                    hashTable.insert(value);
                    break;
                }
                case removeValue: {
                    Output.printString("Write value to remove:");
                    String value = InputReader.inputValue();
                    hashTable.remove(value);
                    break;
                }
                case checkValue: {
                    Output.printString("Write value to check:");
                    String value = InputReader.inputValue();
                    boolean exists = hashTable.contains(value);
                    String result = (exists ? "in" : "not in");
                    Output.printString(String.format("\"%s\" is %s hashtable", value, result));
                    break;
                }
                case showInfo: {
                    Output.printString(hashTable.getStatisticsAsString());
                    break;
                }
                case print: {
                    Output.printString(hashTable.toString());
                    break;
                }
                case checkoutFile: {
                    Output.printString("Write name of file to checkout:");
                    String filename = InputReader.inputFilename();
                    Output.printString("Erase hashtable?(y/n):");
                    if (InputReader.inputChoice())
                        hashTable.erase();
                    try {
                        FileReader fileReader = new FileReader(filename);
                        Scanner fileScanner = new Scanner(fileReader);
                        while (fileScanner.hasNext()) {
                            String value = fileScanner.next();
                            hashTable.insert(value);
                        }
                        fileReader.close();
                    } catch (FileNotFoundException e) {
                        Output.printString(String.format("File \"%s\" was not found.", filename));
                    } catch (IOException e) {
                        Output.printString(String.format("Couldn't close file \"%s\"", filename));
                    }
                    break;
                }
                case changeHash: {
                    Output.printString("Write number of cells:");
                    HashStrategy hashStrategy = InputReader.inputHashStrategy();
                    hashTable.setHashStrategy(hashStrategy);
                    break;
                }
                case close: {
                    isRunning = false;
                    break;
                }
                case help: {
                    Output.printHelp();
                    break;
                }
                default: {
                    Output.printString("Something went wrong\n");
                }
            }
        }
    }
}
