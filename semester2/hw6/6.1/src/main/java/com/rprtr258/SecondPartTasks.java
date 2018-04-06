package com.rprtr258;

import com.sun.java.swing.plaf.windows.WindowsTreeUI;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

public final class SecondPartTasks {
    // Найти строки из переданных файлов, в которых встречается указанная подстрока.
    public static List<String> findQuotes(List<String> paths, CharSequence sequence) {
        return paths.stream().filter(filename -> fileContainsString(filename, sequence))
                .collect(Collectors.toList());
    }

    // В квадрат с длиной стороны 1 вписана мишень.
    // Стрелок атакует мишень и каждый раз попадает в произвольную точку квадрата.
    // Надо промоделировать этот процесс с помощью класса java.util.Random и посчитать, какова вероятность попасть в мишень.
    public static double piDividedBy4() {
        Random rand = new Random();
        final int ATTEMPTS = 10000000;
        int hit = 0;
        for (int i = 0; i < ATTEMPTS; i++) {
            double x = rand.nextDouble() % 1 - 0.5;
            double y = rand.nextDouble() % 1 - 0.5;
            if (x * x + y * y <= 0.5 * 0.5)
                hit++;
        }
        return (double)hit / ATTEMPTS;
    }

    // Дано отображение из имени автора в список с содержанием его произведений.
    // Надо вычислить, чья общая длина произведений наибольшая.
    public static String findPrinter(Map<String, List<String>> compositions) {
        List<Note> notes = new ArrayList<>();
        for (String author : compositions.keySet())
            notes.add(new Note(author, compositions.get(author)));
        Note result = notes.stream().max(Comparator.comparingInt(Note::getSummaryLength)).orElse(new Note("Yurii", null));
        return result.author;
    }

    // Вы крупный поставщик продуктов. Каждая торговая сеть делает вам заказ в виде Map<Товар, Количество>.
    // Необходимо вычислить, какой товар и в каком количестве надо поставить.
    public static Map<String, Integer> calculateGlobalOrder(List<Map<String, Integer>> orders) {
        Map<String, Integer> result = new TreeMap<>();
        for (Map<String, Integer> order : orders)
            for (String product : order.keySet())
                result.put(product, result.get(product) + order.get(product));
        return result;
    }

    private SecondPartTasks() {}

    private static boolean fileContainsString(String filename, CharSequence sequence) {
        try {
            FileInputStream fileStream = new FileInputStream(filename);
            Scanner fileScanner = new Scanner(fileStream);
            // TODO: check correctness
            fileScanner.next(String.valueOf(sequence));
            return true;
        } catch (FileNotFoundException e) {
            return false;
        }
    }

    private static class Note {
        private String author = null;
        private List<String> compositions = null;

        private Note(String author, List<String> compositions) {
            this.author = author;
            this.compositions = compositions;
        }

        private int getSummaryLength() {
            return compositions.stream().mapToInt(String::length).sum();
        }
    }
}
