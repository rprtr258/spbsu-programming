#pragma once
#include "../intList/intList.h"

struct IntStack {
    IntLinkedList *data = nullptr;
};

IntStack* intStackCreate();
void intStackErase(IntStack *stack);
void intStackDelete(IntStack *&stack);

void intStackPush(IntStack *stack, int const value);
int intStackPeek(IntStack *stack);
void intStackPop(IntStack *stack);

int unsigned intStackGetSize(IntStack *stack);
bool intStackIsEmpty(IntStack *stack);

