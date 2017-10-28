#include <utility>
#include "heap.h"

using std::swap;

int max(const int a, const int b) {
    return (a >= b ? a : b);
}

int parent(const int pos) {
    return (pos - 1) / 2;
}

int leftChild(const int pos) {
    return pos * 2 + 1;
}

int rightChild(const int pos) {
    return pos * 2 + 2;
}

bool isInHeap(const Heap &heap, const int pos) {
    return (pos < heap.size);
}

void heapBuildOnArray(Heap &heap, int *array, const int arraySize) {
    heap.data = array;
    heap.size = 0;
    for (int i = 0; i < arraySize; i++) {
        heapSiftUp(heap, i);
        heap.size++;
    }
}

void heapSiftUp(Heap &heap, const int pos) {
    int i = pos;
    while (i > 0 && heap.data[parent(i)] < heap.data[i]) {
        swap(heap.data[i], heap.data[parent(i)]);
        i = parent(i);
    }
}

void heapSiftDown(Heap &heap, int pos) {
    int i = pos;
    while (true) {
        int left = leftChild(i);
        int right = rightChild(i);
        int curValue = heap.data[i];
        
        if (isInHeap(heap, left) && isInHeap(heap, right)) {
            if (heap.data[left] >= heap.data[right] && heap.data[left] > curValue) {
                swap(heap.data[i], heap.data[left]);
                i = left;
            } else if (heap.data[right] >= heap.data[left] && heap.data[right] > curValue) {
                swap(heap.data[i], heap.data[right]);
                i = right;
            } else {
                break;
            }
        } else if (isInHeap(heap, left)) {
            if (heap.data[left] > curValue) {
                swap(heap.data[i], heap.data[left]);
                i = left;
            } else {
                break;
            }
        } else {
            break;
        }
    }
}

int heapPop(Heap &heap) {
    int minVal = heap.data[0];
    
    swap(heap.data[0], heap.data[heap.size - 1]);
    heap.size--;
    heapSiftDown(heap, 0);
    
    return minVal;
}

void heapSort(int *array, const int arraySize) {
    Heap heap;
    heapBuildOnArray(heap, array, arraySize);
    
    while (heap.size > 0) {
        heapPop(heap);
    }
}