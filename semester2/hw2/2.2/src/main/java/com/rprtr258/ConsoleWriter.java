package com.rprtr258;

public class ConsoleWriter implements IWriter {
    public void write(int value) {
        System.out.print(value);
    }

    public void write(char symbol) {
        System.out.print(symbol);
    }

    public void close() {
    }
}
