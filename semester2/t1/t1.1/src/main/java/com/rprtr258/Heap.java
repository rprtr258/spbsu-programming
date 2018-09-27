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
        siftUp(size() - 1);
    }

    /**
     * Pops value with highest priority from heap and returns it.
     * @return value in heap which has highest priority.
     * @throws EmptyHeapException if heap is empty.
     */
    public T dequeue() throws EmptyHeapException {
        if (isEmpty())
            throw new EmptyHeapException();

        T result = data.get(0).value;
        swap(0, size() - 1);
        data.remove(size() - 1);
        if (!isEmpty())
            siftDown(0);

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

    private int parent(int pos) {
        return (pos - 1) / 2;
    }

    private int leftChild(int pos) {
        return pos * 2 + 1;
    }

    private int rightChild(int pos) {
        return pos * 2 + 2;
    }

    private boolean isInHeap(int pos) {
        return (pos < size());
    }

    private void swap(int i, int j) {
        Note<T> temp = data.get(i);
        data.set(i, data.get(j));
        data.set(j, temp);
    }

    private void siftUp(int pos) {
        int i = pos;
        while (i > 0 && data.get(parent(i)).priority < data.get(i).priority) {
            swap(i, parent(i));
            i = parent(i);
        }
    }

    private void siftDown(int pos) {
        int i = pos;
        while (true) {
            int left = leftChild(i);
            int right = rightChild(i);
            int curPrior = data.get(i).priority;

            if (isInHeap(left) && isInHeap(right)) {
                int leftPrior = data.get(left).priority;
                int rightPrior = data.get(right).priority;
                if (leftPrior >= rightPrior && leftPrior > curPrior) {
                    swap(i, left);
                    i = left;
                } else if (rightPrior >= leftPrior && rightPrior > curPrior) {
                    swap(i, right);
                    i = right;
                } else {
                    break;
                }
            } else if (isInHeap(left)) {
                int leftPrior = data.get(left).priority;
                if (leftPrior > curPrior) {
                    swap(i, left);
                    i = left;
                } else {
                    break;
                }
            } else {
                break;
            }
        }
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
