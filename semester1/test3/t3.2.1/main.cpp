#include <stdio.h>
#include "priorityQueue.h"

int main() {
    PriorityQueue *priorityQueue = priorQueueCreate();
    
    priorQueueDelete(priorityQueue);
    return 0;
}

