#pragma once

struct Heap {
    int *data = nullptr;
    int size = -1;
};

int parent(const int pos);
int leftChild(const int pos);
int rightChild(const int pos);
bool isInHeap(const Heap &heap, const int pos);
 
void heapBuildOnArray(Heap &heap, int *array, const int arraySize);
void heapSiftUp(Heap &heap, const int pos);
void heapSiftDown(Heap &heap, int pos);
int heapPop(Heap &heap);

void heapSort(int *array, int arraySize);