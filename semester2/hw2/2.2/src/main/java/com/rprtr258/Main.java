package com.rprtr258;

import java.io.File;
import java.util.Scanner;

public class Main {
    private static Scanner in = new Scanner(System.in);

    private static int inputMatrixSize() {
        System.out.print("Write size of array:\n");
        int result = in.nextInt();
        while (result <= 0 || result % 2 == 0) {
            System.out.print("Incorrect size, please retype:\n");
            result = in.nextInt();
        }
        return result;
    }

    private static Matrix inputMatrix(int matrixSize) {
        System.out.printf("Write %d numbers of matrix(row by row):\n", matrixSize * matrixSize);
        int[][] result = new int[matrixSize][matrixSize];
        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                result[i][j] = in.nextInt();
            }
        }
        return new Matrix(result);
    }

    private static FileWriter inputFile() {
        System.out.print("Write file name:\n");
        String filename = in.nextLine();
        while (!filename.matches("\\w+")) {
            System.out.print("Incorrect name\n");
            System.out.print("Write file name:\n");
            filename = in.nextLine();
        }
        File file = new File(String.format("%s.txt", filename));
        return new FileWriter(file);
    }

    private static IWriter inputPrintType() {
        System.out.print("Choose print type(console/file):\n");
        String printType = in.nextLine();
        while (!printType.equals("console") && !printType.equals("file")) {
            System.out.print("Wrong print type.\n");
            System.out.print("Choose print type(console/file):\n");
            printType = in.nextLine();
        }
        switch (printType) {
            case "console": {
                return new ConsoleWriter();
            }
            case "file": {
                return inputFile();
            }
            default: {
                System.out.print("You hacked program in some way.\n");
                System.out.print("Please report about it to poletansky(at)gmail.com\n");
                return null;
            }
        }
    }

    public static void main(String[] args) {
        int matrixSize = inputMatrixSize();
        Matrix matrix = inputMatrix(matrixSize);
        in.nextLine();
        IWriter writer = inputPrintType();
        matrix.writeTourTo(writer);
    }
}
