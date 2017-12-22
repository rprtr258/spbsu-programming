#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include "priorityQueue.h"

int main() {
    srand(time(nullptr));
    printf("Priority queue simple test\n");
    printf("Elements are (value, priority)\n");
    
    PriorityQueue *priorityQueue = priorQueueCreate();
    for (int i = 0; i < 10; i++) {
        int value = rand() % 100;
        int priority = (rand() % 100 + 100) % 100;
        printf("Put (%d, %d) in queue\n", value, priority);
        enqueue(priorityQueue, value, priority);
    }
    printf("\n");
    printf("Queue size: %d\n", priorityQueue->size);
    printf("\n");
    for (int i = 0; i < 10; i++) {
        int value = dequeue(priorityQueue);
        printf("Got %d from top of queue\n", value);
    }
    printf("\n");
    printf("Queue size: %d\n", priorityQueue->size);
    printf("Got %d from top of queue\n", dequeue(priorityQueue));
    priorQueueDelete(priorityQueue);
    return 0;
}

