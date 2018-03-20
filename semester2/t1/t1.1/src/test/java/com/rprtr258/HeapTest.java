package com.rprtr258;

import org.junit.Test;

import static org.junit.Assert.*;

public class HeapTest {
    @Test
    public void enqueElementTest() {
        Heap<String> heap = makeHeap();
        assertEquals("[I, AM, THE, GOD]", heap.toString());
    }

    @Test
    public void dequeElementTest() throws EmptyHeapException {
        Heap<String> heap = makeHeap();
        assertEquals("I", heap.dequeue());
        assertEquals("AM", heap.dequeue());
        heap.enqueue("IS", 9);
        heap.enqueue("HE", 20);
        assertEquals("[HE, IS, THE, GOD]", heap.toString());
    }

    @Test
    public void isEmptyTest() throws EmptyHeapException {
        Heap<String> heap = makeHeap();
        int heapSize = heap.size();
        for (int i = 0; i < heapSize; i++) {
            assertFalse(heap.isEmpty());
            heap.dequeue();
        }
        assertTrue(heap.isEmpty());
    }

    @Test(expected = EmptyHeapException.class)
    public void emptyHeapExceptionTest() throws EmptyHeapException {
        Heap<String> heap = makeHeap();
        int heapSize = heap.size();
        for (int i = 0; i < heapSize; i++)
            heap.dequeue();
        heap.dequeue();
    }

    private Heap<String> makeHeap() {
        Heap<String> heap = new Heap<>();
        heap.enqueue("GOD", -12);
        heap.enqueue("I", 10);
        heap.enqueue("THE", 4);
        heap.enqueue("AM", 7);
        return heap;
    }
}