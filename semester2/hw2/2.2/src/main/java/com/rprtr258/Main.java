package com.rprtr258;

import java.io.File;
import java.util.Scanner;

public class Main {
    static Scanner in = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.print("Write size of array:\n");
        int matrixSize = in.nextInt();
        System.out.printf("Write %d numbers of matrix(row by row):\n", matrixSize * matrixSize);
        int[][] matrixData = new int[matrixSize][matrixSize];
        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                matrixData[i][j] = in.nextInt();
            }
        }
        Matrix matrix = new Matrix(matrixData);
        System.out.print("Choose print type(console/file):\n");
        in.nextLine();
        String printType = in.nextLine();
        switch (printType) {
            case "console": {
                matrix.writeTourTo(new ConsoleWriter());
                break;
            }
            case "file": {
                System.out.print("Write file name:\n");
                String filename = in.nextLine();
                File file = new File(filename);
                matrix.writeTourTo(new FileWriter(file));
                break;
            }
        }
    }
}
