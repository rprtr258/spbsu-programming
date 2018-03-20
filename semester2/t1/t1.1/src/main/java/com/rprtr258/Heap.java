package com.rprtr258;

import java.util.ArrayList;

/**
 * Heap(priority queue) data structure. In this implementation values with high priority
 * will be popped(dequeue method) earlier than others.
 * <a href = "https://en.wikipedia.org/wiki/Priority_queue">Wikipedia</a>
 * @param <T> type of stored values.
 */
public class Heap<T> {
    private ArrayList<Note<T>> data = null;

    /**
     * Constructs empty heap.
     */
    public Heap() {
        data = new ArrayList<>();
    }

    /**
     * Enqueues value to the heap.
     * @param value value to enqueue.
     * @param priority priority of that value.
     */
    public void enqueue(T value, int priority) {
        data.add(new Note<>(value, priority));
        int i = data.size() - 1;
        while (i > 0 && priority > data.get(i - 1).priority) {
            Note<T> temp = data.get(i);
            data.set(i, data.get(i - 1));
            data.set(i - 1, temp);
            i--;
        }
    }

    /**
     * Pops value with highest priority from heap and returns it.
     * @return value in heap which has highest priority.
     * @throws EmptyHeapException if heap is empty.
     */
    public T dequeue() throws EmptyHeapException {
        if (data.isEmpty())
            throw new EmptyHeapException();
        T result = data.get(0).value;
        data.remove(0);
        return result;
    }

    /**
     * Checks if heap is empty.
     * @return true if heap is empty.
     */
    public boolean isEmpty() {
        return data.isEmpty();
    }

    /**
     * Returns number of values pushed to heap.
     * @return number of elements in heap.
     */
    public int size() {
        return data.size();
    }

    /**
     * Returns string representation of data array.
     * @return string of values stored in heap in decreasing order according to priority.
     */
    @Override
    public String toString() {
        ArrayList<String> dataStrings = new ArrayList<>();
        for (Note<T> note : data)
            dataStrings.add(note.value.toString());
        return dataStrings.toString();
    }

    /**
     * Inner class which just stores value and it's priority.
     * @param <E> type of stored value.
     */
    private class Note<E> {
        private E value = null;
        private int priority = -1;

        /**
         * Constructs note with given value and priority.
         * @param value value to be stored.
         * @param priority priority of that value.
         */
        private Note(E value, int priority) {
            this.value = value;
            this.priority = priority;
        }
    }
}