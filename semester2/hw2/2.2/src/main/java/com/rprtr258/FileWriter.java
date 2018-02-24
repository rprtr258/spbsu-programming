package com.rprtr258;

import java.io.*;

public class FileWriter implements IWriter {
    private FileOutputStream fis = null;

    public FileWriter(File file) {
        try {
            fis = new FileOutputStream(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(int value) {
        try {
            fis.write(String.valueOf(value).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(char symbol) {
        try {
            fis.write(symbol);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
