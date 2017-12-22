#include <string.h>
#include <utility>
#include <algorithm>
#include "priorityQueue.h"

struct PriorQueueElement {
    int value = 0;
    int priority = 0;
};

PriorQueueElement* createElement(int const value, int const priority) {
    PriorQueueElement *result = new PriorQueueElement();
    result->priority = priority;
    result->value = value;
    return result;
}

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
        int leftPriority = heap->data[left].priority;
        int rightPriority = heap->data[right].priority;
        
        if (isInHeap(heap, left) && isInHeap(heap, right)) {
            if (leftPriority >= rightPriority && leftPriority > curPriority) {
                swap(heap->data[i], heap->data[left]);
                i = left;
            } else if (rightPriority >= leftPriority && rightPriority > curPriority) {
                swap(heap->data[i], heap->data[right]);
                i = right;
            } else {
                break;
            }
        } else if (isInHeap(heap, left)) {
            if (leftPriority > curPriority) {
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
    PriorQueueElement *newData = new PriorQueueElement[newSize];
    memcpy(newData, heap->data, sizeof(PriorQueueElement) * heap->size);
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
    
    PriorQueueElement *newElement = createElement(value, priority);
    memcpy(heap->data + heap->size, newElement, sizeof(PriorQueueElement));
    delete newElement;
    heapSiftUp(heap, heap->size);
    heap->size++;
}

int dequeue(PriorityQueue *heap) {
    if (heap == nullptr || heap->size == 0)
        return -1;
    
    PriorQueueElement maxVal = heap->data[0];
    
    swap(heap->data[0], heap->data[heap->size - 1]);
    heap->size--;
    heapSiftDown(heap, 0);
    
    return maxVal.value;
}
