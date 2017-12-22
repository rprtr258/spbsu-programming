#include <string.h>
#include <utility>
#include <algorithm>
#include "priorityQueue.h"

struct HeapElement {
    int value = 0;
    int priority = 0;
};

using std::swap;
using std::max;

int parent(const int pos) {
    return (pos - 1) / 2;
}

int leftChild(const int pos) {
    return pos * 2 + 1;
}

int rightChild(const int pos) {
    return pos * 2 + 2;
}


bool isInHeap(const PriorityQueue *heap, const int pos) {
    return (pos < heap->size);
}

void heapSiftUp(PriorityQueue *heap, const int pos) {
    int i = pos;
    while (i > 0 && heap->data[parent(i)].priority < heap->data[i].priority) {
        swap(heap->data[i], heap->data[parent(i)]);
        i = parent(i);
    }
}

void heapSiftDown(PriorityQueue *heap, int pos) {
    int i = pos;
    while (true) {
        int left = leftChild(i);
        int right = rightChild(i);
        int curPriority = heap->data[i].priority;
        
        if (isInHeap(heap, left) && isInHeap(heap, right)) {
            if (heap->data[left].priority >= heap->data[right].priority && heap->data[left].priority > curPriority) {
                swap(heap->data[i], heap->data[left]);
                i = left;
            } else if (heap->data[right].priority >= heap->data[left].priority && heap->data[right].priority > curPriority) {
                swap(heap->data[i], heap->data[right]);
                i = right;
            } else {
                break;
            }
        } else if (isInHeap(heap, left)) {
            if (heap->data[left].priority > curPriority) {
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

void resize(PriorityQueue *&heap, int const newSize) {
    HeapElement *newData = new HeapElement[newSize];
    memcpy(newData, heap->data, sizeof(HeapElement) * heap->size);
    heap->capacity *= 2;
    delete[] heap->data;
    heap->data = newData;
}

PriorityQueue *priorQueueCreate() {
    PriorityQueue *result = new PriorityQueue();
    result->size = 0;
    result->capacity = 0;
    return result;
}

void priorQueueDelete(PriorityQueue *&heap) {
    if (heap == nullptr)
        return;
    delete[] heap->data;
    delete heap;
    heap = nullptr;
}

void enqueue(PriorityQueue *heap, int const value, int const priority) {
    if (heap == nullptr)
        return;
    
    if (heap->capacity < heap->size + 1)
        resize(heap, heap->size * 2);
    
    HeapElement *newElement = new HeapElement();
    newElement->value = value;
    newElement->priority = priority;
    memcpy(heap->data + heap->size, newElement, sizeof(HeapElement));
    delete newElement;
    heapSiftUp(heap, heap->size);
    heap->size++;
}

int dequeue(PriorityQueue *heap) {
    if (heap == nullptr || heap->size == 0)
        return -1;
    
    HeapElement maxVal = heap->data[0];
    
    swap(heap->data[0], heap->data[heap->size - 1]);
    heap->size--;
    heapSiftDown(heap, 0);
    
    return maxVal.value;
}
