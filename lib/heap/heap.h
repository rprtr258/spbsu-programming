#pragma once

struct Heap {
    int *data = nullptr;
    int unsigned capacity = 0;
    int unsigned size = 0;
};

Heap* heapCreate();
Heap* heapBuildOnArray(int *array, int unsigned arraySize);
void heapDelete(Heap *&heap);

void heapPush(Heap *&heap, int const value);
int heapPeek(Heap *heap);
int heapPop(Heap *heap);

