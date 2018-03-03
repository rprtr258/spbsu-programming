package com.rprtr258;

import com.rprtr258.hashtable.*;

import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        InputReader.setInput(new Scanner(System.in));
        boolean isRunning = true;
        HashTable hashTable = new HashTable(new CharSumHash());
        while (isRunning) {
            CommandType command = InputReader.inputCommand();
            switch (command) {
                case invalid: {
                    System.out.print("Incorrect command. Write help to see list of commands.\n");
                    break;
                }
                case addValue: {
                    System.out.print("Write value to add:\n");
                    String value = InputReader.inputValue();
                    hashTable.insert(value);
                    break;
                }
                case removeValue: {
                    System.out.print("Write value to remove:\n");
                    String value = InputReader.inputValue();
                    hashTable.remove(value);
                    break;
                }
                case checkValue: {
                    System.out.print("Write value to check:\n");
                    String value = InputReader.inputValue();
                    boolean exists = hashTable.contains(value);
                    if (exists)
                        System.out.printf("\"%s\" is in hashtable\n", value);
                    else
                        System.out.printf("\"%s\" is not in hashtable\n", value);
                    break;
                }
                case showInfo: {
                    System.out.print(hashTable.getStatisticsAsString() + "\n");
                    break;
                }
                case print: {
                    System.out.print(hashTable);
                    break;
                }
                case checkoutFile: {
                    System.out.print("Write name of file to checkout:\n");
                    String filename = InputReader.inputFilename();
                    System.out.print("Erase hashtable?(y/n):\n");
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
                        System.out.printf("File \"%s\" was not found.\n", filename);
                    } catch (IOException e) {
                        System.out.printf("Couldn't close file \"%s\".\n", filename);
                    }
                    break;
                }
                case changeHash: {
                    System.out.print("Write number of cells:\n");
                    HashStrategy hashStrategy = InputReader.inputHashStrategy();
                    hashTable.setHashStrategy(hashStrategy);
                    break;
                }
                case close: {
                    isRunning = false;
                    break;
                }
                case help: {
                    System.out.print("List of available commands:\n");
                    System.out.print("add - add value to hashtable\n");
                    System.out.print("remove - remove value to hashtable\n");
                    System.out.print("info - get statistics of hashtable\n");
                    System.out.print("print - print hashtable\n");
                    System.out.print("fill - fill hashtable with values from file\n");
                    System.out.print("hash - change hash function\n");
                    System.out.print("quit - exit program\n");
                    System.out.print("help - show this list\n");
                    break;
                }
            }
        }
    }
}
