#include <stdio.h>
#include "intQueue.h"

IntQueue* intQueueCreate() {
    IntQueue *result = new IntQueue();
    result->data = intListCreate();
    return result;
}

void intQueueErase(IntQueue *queue) {
    if (queue == nullptr)
        return;
    
    intListErase(queue->data);
}

void intQueueDelete(IntQueue *&queue) {
    if (queue == nullptr)
        return;
    
    intListDelete(queue->data);
    delete queue;
    queue = nullptr;
}

void intQueuePush(IntQueue *queue, int const value) {
    if (queue == nullptr)
        return;
    
    intListInsertAtEnd(queue->data, value);
}

int intQueuePeek(IntQueue *queue) {
    if (queue == nullptr)
        return -1;
    
    return intListPeekBegin(queue->data);
}

void intQueuePop(IntQueue *queue) {
    if (queue == nullptr)
        return;
    
    intListDeleteBegin(queue->data);
}

int unsigned intQueueGetSize(IntQueue *queue) {
    if (queue == nullptr || queue->data == nullptr)
        return -1;
    
    return queue->data->size;
}

bool intQueueIsEmpty(IntQueue *queue) {
    return (intQueueGetSize(queue) == 0);
}

