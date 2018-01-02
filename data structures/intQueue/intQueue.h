#pragma once
#include "../intList/intList.h"

struct IntQueue {
    IntLinkedList *data = nullptr;
};

IntQueue* intQueueCreate();
void intQueueErase(IntQueue *queue);
void intQueueDelete(IntQueue *&queue);

void intQueuePush(IntQueue *queue, int const value);
int intQueuePeek(IntQueue *queue);
void intQueuePop(IntQueue *queue);

int unsigned intQueueGetSize(IntQueue *queue);
bool intQueueIsEmpty(IntQueue *queue);

