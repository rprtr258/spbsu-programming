#include <stdio.h>
#include "intStack.h"

IntStack* intStackCreate() {
    IntStack *result = new IntStack();
    result->data = intListCreate();
    return result;
}

void intStackErase(IntStack *stack) {
    if (stack == nullptr)
        return;
    
    intListErase(stack->data);
}

void intStackDelete(IntStack *&stack) {
    if (stack == nullptr)
        return;
    
    intListDelete(stack->data);
    delete stack;
    stack = nullptr;
}

void intStackPush(IntStack *stack, int const value) {
    if (stack == nullptr)
        return;
    
    intListInsertAtBegin(stack->data, value);
}

int intStackPeek(IntStack *stack) {
    if (stack == nullptr)
        return -1;
    
    return intListPeekBegin(stack->data);
}

void intStackPop(IntStack *stack) {
    if (stack == nullptr)
        return;
    
    intListDeleteBegin(stack->data);
}

int intStackGetSize(IntStack *stack) {
    if (stack == nullptr || stack->data == nullptr)
        return -1;
    
    return stack->data->size;
}

bool intStackIsEmpty(IntStack *stack) {
    return (intStackGetSize(stack) == 0);
}

