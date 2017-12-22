#pragma once

struct HeapElement;

struct PriorityQueue {
    HeapElement *data = nullptr;
    int size = -1;
    int capacity = -1;
};

PriorityQueue *priorQueueCreate();
void priorQueueDelete(PriorityQueue *&heap);

void enqueue(PriorityQueue *heap, int const value, int const priority);
int dequeue(PriorityQueue *heap);
