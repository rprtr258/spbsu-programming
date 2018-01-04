#include <utility>
#include "heap.h"

using std::swap;

int min(const int a, const int b) {
    return (a < b ? a : b);
}

int parent(int unsigned const pos) {
    return (pos - 1) / 2;
}

int leftChild(int unsigned const pos) {
    return pos * 2 + 1;
}

int rightChild(int unsigned const pos) {
    return pos * 2 + 2;
}

bool isInHeap(Heap *heap, int unsigned const pos) {
    return (pos < heap->size);
}

void siftUp(Heap *heap, int unsigned const pos) {
    int unsigned i = pos;
    while (i > 0 && heap->data[parent(i)] > heap->data[i]) {
        swap(heap->data[i], heap->data[parent(i)]);
        i = parent(i);
    }
}

void siftDown(Heap *heap, int unsigned const pos) {
    int unsigned i = pos;
    while (true) {
        int unsigned left = leftChild(i);
        int unsigned right = rightChild(i);
        int leftValue = heap->data[left];
        int rightValue = heap->data[right];
        int curValue = heap->data[i];
        
        if (isInHeap(heap, left) && isInHeap(heap, right)) {
            if (leftValue <= rightValue && leftValue < curValue) {
                swap(heap->data[i], heap->data[left]);
                i = left;
            } else if (rightValue <= leftValue && rightValue < curValue) {
                swap(heap->data[i], heap->data[right]);
                i = right;
            } else {
                break;
            }
        } else if (isInHeap(heap, left)) {
            if (leftValue < curValue) {
                swap(heap->data[i], heap->data[left]);
                i = left;
            } else {
                break;
            }
        } else {
            break;
        }
    }
}

void resize(Heap *&heap, int unsigned const newCapacity) {
    Heap *newHeap = heapCreate();
    newHeap->data = new int[newCapacity];
    newHeap->size = heap->size;
    newHeap->capacity = newCapacity;
    
    for (int unsigned i = 0; i < heap->size; i++)
        newHeap->data[i] = heap->data[i];
    
    heapDelete(heap);
    heap = newHeap;
}

Heap* heapCreate() {
    Heap *result = new Heap();
    return result;
}

Heap* heapBuildOnArray(int *array, int unsigned const arraySize) {
    Heap *result = heapCreate();
    result->size = arraySize;
    result->capacity = arraySize;
    result->data = new int[arraySize];
    for (int unsigned i = 0; i < arraySize; i++) {
        result->data[i] = array[i];
        siftUp(result, i);
    }
    return result;
}

void heapDelete(Heap *&heap) {
    if (heap == nullptr)
        return;
    
    if (heap->data != nullptr)
        delete[] heap->data;
    delete heap;
    heap = nullptr;
}

void heapPush(Heap *&heap, int const value) {
    if (heap == nullptr) {
        throw 1;
        return;
    }
    if (heap->size == heap->capacity)
        resize(heap, heap->capacity * 2 + 1);
    
    heap->data[heap->size] = value;
    heap->size++;
    siftUp(heap, heap->size - 1);
}

int heapPeek(Heap *heap) {
    if (heap == nullptr || heap->size < 1) {
        throw 1;
        return -1;
    }
    
    return heap->data[0];
}

int heapPop(Heap *heap) {
    if (heap == nullptr || heap->size < 1) {
        throw 1;
        return -1;
    }
    
    int result = heap->data[0];
    
    heap->size--;
    swap(heap->data[0], heap->data[heap->size]);
    siftDown(heap, 0);
    
    return result;
}

