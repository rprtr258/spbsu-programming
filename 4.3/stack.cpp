#include "stack.h"

struct Stack {
    LinkedList *list = createList();
};

Stack* createStack() {
    return new Stack;
}

void stackPush(Stack *stack, const char val) {
    listPushBack(stack->list, val);
}

char stackTop(Stack *stack) {
    return listGetLast(stack->list);
}

void stackPop(Stack *stack) {
    listPopBack(stack->list);
}

bool stackIsEmpty(Stack *stack) {
    return listIsEmpty(stack->list);
}

void stackErase(Stack *stack) {
    listErase(stack->list);
}
