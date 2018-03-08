package com.rprtr258;

import com.rprtr258.hashtable.*;

import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Input.setIn(new Scanner(System.in));
        Output.setOut(System.out);
        boolean isRunning = true;
        HashTable hashTable = new HashTable(new CharSumHash());
        while (isRunning) {
            CommandType command = Input.inputCommand();
            switch (command) {
                case addValue: {
                    Output.printlnString("Write value to add:");
                    String value = Input.inputValue();
                    hashTable.insert(value);
                    break;
                }
                case removeValue: {
                    Output.printlnString("Write value to remove:");
                    String value = Input.inputValue();
                    hashTable.remove(value);
                    break;
                }
                case checkValue: {
                    Output.printlnString("Write value to check:");
                    String value = Input.inputValue();
                    boolean exists = hashTable.contains(value);
                    String result = (exists ? "in" : "not in");
                    Output.printlnString(String.format("\"%s\" is %s hashtable", value, result));
                    break;
                }
                case showInfo: {
                    Output.printlnString(hashTable.getStatisticsAsString());
                    break;
                }
                case print: {
                    Output.printlnString(hashTable.toString());
                    break;
                }
                case checkoutFile: {
                    Output.printlnString("Write name of file to checkout:");
                    String filename = Input.inputFilename();
                    Output.printlnString("Erase hashtable?(y/n):");
                    if (Input.inputChoice())
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
                        Output.printlnString(String.format("File \"%s\" was not found.", filename));
                    } catch (IOException e) {
                        Output.printlnString(String.format("Couldn't close file \"%s\"", filename));
                    }
                    break;
                }
                case changeHash: {
                    Output.printlnString("Write number of cells:");
                    HashStrategy hashStrategy = Input.inputHashStrategy();
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
                    Output.printlnString("Something went wrong\n");
                }
            }
        }
    }
}
