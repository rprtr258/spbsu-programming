package com.rprtr258;

import java.util.ArrayList;
import java.util.LinkedList;

public class HashTable {
    private HashStrategy hashStrategy = null;
    private ArrayList<LinkedList<String>> data = null;

    public HashTable(HashStrategy hashStrategy) {
        this.hashStrategy = hashStrategy;
        data = createDataArray(hashStrategy);
    }

    public void insert(String string) {
        if (contains(string))
            return;
        int hash = hashStrategy.hash(string);
        data.get(hash).add(string);
    }

    public void remove(String string) {
        if (!contains(string))
            return;
        int hash = hashStrategy.hash(string);
        data.get(hash).remove(string);
    }

    public boolean contains(String string) {
        int hash = hashStrategy.hash(string);
        return data.get(hash).contains(string);
    }

    public int getElementsCount() {
        int result = 0;
        for (LinkedList<String> row : data)
            result += row.size();
        return result;
    }

    private double getLoadFactor() {
        return ((double)getElementsCount()) / (hashStrategy.maxHashValue() + 1);
    }

    public String getStatisticsAsString() {
        int cells = getElementsCount();
        double loadFactor = getLoadFactor();
        int maxChainLength = 0;
        for (LinkedList<String> row : data)
            maxChainLength = Integer.max(maxChainLength, row.size());
        int conflicts = 0;
        for (LinkedList<String> row : data)
            if (row.size() > 1)
                conflicts++;
        String cellsInfo = String.format("Cells: %d", cells);
        String loadFactorInfo = String.format("Load factor: %f", loadFactor);
        String conflictsInfo = String.format("Conflicts: %d", conflicts);
        String maxChainInfo = String.format("Max chain length: %d", maxChainLength);
        return String.join("\n", cellsInfo, loadFactorInfo, conflictsInfo, maxChainInfo);
    }

    public void erase() {
        data.forEach(LinkedList::clear);
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(String.format("Hashtable of size %d:\n", data.size()));
        for (int i = 0; i < data.size(); i++) {
            LinkedList<String> row = data.get(i);
            if (row.size() == 0)
                continue;
            result.append(i).append(": ");
            for (String value : row) {
                result.append(value).append(", ");
            }
            result.append("\n");
        }
        return result.toString();
    }

    public void setHashStrategy(HashStrategy hashStrategy) {
        ArrayList<LinkedList<String>> newData = createDataArray(hashStrategy);
        for (LinkedList<String> row : data) {
            for (String value : row) {
                int hash = hashStrategy.hash(value);
                newData.get(hash).add(value);
            }
        }
        this.data = newData;
        this.hashStrategy = hashStrategy;
    }

    private ArrayList<LinkedList<String>> createDataArray(HashStrategy hashStrategy) {
        ArrayList<LinkedList<String>> data = new ArrayList<>(hashStrategy.maxHashValue() + 1);
        for (int i = 0; i <= hashStrategy.maxHashValue(); i++)
            data.add(new LinkedList<>());
        return data;
    }
}
