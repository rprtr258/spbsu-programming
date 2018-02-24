package com.rprtr258;

import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class MatrixTest {
    @Test
    public void spiralTourTest() {
        int[][] sampleMatrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        Matrix matrix = new Matrix(sampleMatrix);
        ArrayList<Integer> tour = matrix.spiralTour();
        List<Integer> expected = Arrays.asList(5, 4, 7, 8, 9, 6, 3, 2, 1);
        assertEquals(expected, tour);
    }

    @Test
    public void printConsoleTest() {
        Matrix matrix = new Matrix(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}});
        IWriter writer = new ConsoleWriter();
        matrix.writeTourTo(writer);
        assertTrue(true);
    }

    private String readFile(File file) {
        String result = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            result = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Test
    public void printFileTest() {
        Matrix matrix = new Matrix(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}});
        File file = null;
        try {
            file = File.createTempFile("matrix", "txt");
            IWriter writer = new FileWriter(file);
            matrix.writeTourTo(writer);
            assertEquals("5 4 7 8 9 6 3 2 1", readFile(file));
            file.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}