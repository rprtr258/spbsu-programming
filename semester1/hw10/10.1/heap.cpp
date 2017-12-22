#include <stdlib.h>
#include <string.h>
#include <utility>
#include "heap.h"

using std::swap;

NodeInfo* max(Heap *heap, NodeInfo *a, NodeInfo *b) {
    return (a->h >= b->h ? a : b);
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

bool heapIsIn(const Heap *heap, const int pos) {
    return (pos < heap->size);
}

int getKeyAt(Heap *heap, int const pos) {
    return heap->data[pos]->h;
}

void heapSiftUp(Heap *heap, const int pos) {
    int i = pos;
    while (i > 0 && getKeyAt(heap, parent(i)) > getKeyAt(heap, i)) {
        swap(heap->data[i], heap->data[parent(i)]);
        i = parent(i);
    }
}

void heapSiftDown(Heap *heap, int pos) {
    int i = pos;
    while (true) {
        int left = leftChild(i);
        int right = rightChild(i);
        NodeInfo *curValue = heap->data[i];
        
        if (heapIsIn(heap, right)) {
            if (getKeyAt(heap, left) <= getKeyAt(heap, right) && getKeyAt(heap, left) < curValue->h) {
                swap(heap->data[i], heap->data[left]);
                i = left;
            } else if (getKeyAt(heap, right) <= getKeyAt(heap, left) && getKeyAt(heap, right) < curValue->h) {
                swap(heap->data[i], heap->data[right]);
                i = right;
            } else {
                break;
            }
        } else if (heapIsIn(heap, left)) {
            if (getKeyAt(heap, left) < curValue->h) {
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

Heap* heapCreate() {
    Heap *result = new Heap();
    result->size = 0;
    result->capacity = 0;
    return result;
}

void heapDelete(Heap *&heap) {
    if (heap == nullptr)
        return;
    if (heap->data != nullptr) {
        for (int i = 0; i < heap->size; i++)
            nodeInfoDelete(heap->data[i]);
        delete[] heap->data;
    }
    delete heap;
    heap = nullptr;
}

void resize(Heap *heap, int newCapacity) {
    if (heap == nullptr || newCapacity < heap->size)
        return;
    NodeInfo **newData = new NodeInfo*[newCapacity];
    if (heap->data != nullptr && heap->size > 0)
        memcpy(newData, heap->data, heap->size * sizeof(NodeInfo*));
    if (heap->data != nullptr)
        delete[] heap->data;
    heap->data = newData;
    heap->capacity = newCapacity;
}

void heapPush(Heap *heap, NodeInfo *value) {
    if (heap == nullptr || value == nullptr)
        return;
    if (heap->size == heap->capacity)
        resize(heap, heap->capacity * 2 + 1);
    heap->data[heap->size] = nodeInfoCopy(value);
    heap->size++;
    heapSiftUp(heap, heap->size - 1);
}

NodeInfo* heapPop(Heap *heap) {
    NodeInfo *minVal = nodeInfoCopy(heap->data[0]);
    
    swap(heap->data[0], heap->data[heap->size - 1]);
    heap->size--;
    heapSiftDown(heap, 0);
    
    return minVal;
}
