package com.rprtr258;

import java.util.Scanner;

public class Main {
    private static Scanner in = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.print("Write expression to calc\n");
        String expr = in.nextLine();
        System.out.println(expr);
    }
}
