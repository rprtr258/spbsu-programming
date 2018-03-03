package com.rprtr258;

/*
* Реализовать класс для работы с хеш-таблицей (на списках).
* Общение с пользователем должно происходит в интерактивном режиме:
* добавить значение в хеш-таблицу, удалить значение из хеш-таблицы,
* поиск значения в хеш-таблице, показать статистику по хеш-таблице
* (общее число ячеек, load factor, число конфликтов, максимальная длина
* списка в конфликтных ячейках и т.п.), заполнить хеш-таблицу содержимым файла,
* выбрать хеш-функцию для подсчета хеша (из заранее заданных в коде).
* Смена хэш-функции должна происходить во время работы программы,
* в класс используемая хеш-функция должна передаваться из клиентского кода.
* */

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

enum CommandType {
    invalid,
    addValue,
    removeValue,
    checkValue,
    showInfo,
    print,
    checkoutFile,
    changeHash,
    close,
    help
}

public class Main {
    public static void main(String[] args) {
        boolean isRunning = true;
        Scanner in = new Scanner(System.in);
        HashTable hashTable = new HashTable(new CharSumHash());
        while (isRunning) {
            System.out.print("> ");
            String command = in.nextLine();
            CommandType commandType = getCommandType(command);
            switch (commandType) {
                case invalid: {
                    System.out.print("Incorrect command. Write help to see list of commands.\n");
                    break;
                }
                case addValue: {
                    System.out.print("Write value to add:\n");
                    System.out.print("> ");
                    String value = in.nextLine();
                    hashTable.insert(value);
                    break;
                }
                case removeValue: {
                    System.out.print("Write value to remove:\n");
                    System.out.print("> ");
                    String value = in.nextLine();
                    hashTable.remove(value);
                    break;
                }
                case checkValue: {
                    System.out.print("Write value to check:\n");
                    System.out.print("> ");
                    String value = in.nextLine();
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
                    System.out.print("> ");
                    String filename = in.nextLine();
                    System.out.print("Reset hashtable(y/n)):\n");
                    System.out.print("> ");
                    String choice = in.nextLine();
                    if ("n".equals(choice))
                        hashTable.erase();
                    FileReader fileReader = null;
                    try {
                        fileReader = new FileReader(filename);
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
                    System.out.print("> ");
                    int newBound = Integer.valueOf(in.nextLine());
                    hashTable.setHashStrategy(new CharSumHash(newBound));
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

    private static CommandType getCommandType(String command) {
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
                return CommandType.invalid;
        }
    }
}
