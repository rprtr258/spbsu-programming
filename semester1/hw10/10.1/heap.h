#pragma once
#include "nodeinfo.h"

struct Heap {
    NodeInfo **data = nullptr;
    int size = -1;
    int capacity = -1;
};

Heap* heapCreate();
void heapDelete(Heap *&heap);

bool heapIsIn(const Heap *heap, const NodeInfo pos);

void heapPush(Heap *heap, NodeInfo *value);
NodeInfo* heapPop(Heap *heap);
