#include <stdio.h>
#include "stack.h"

Stack* createStack() {
    Stack *temp = new Stack;
    temp->list = createList();
    return temp;
}

void stackPush(Stack *stack, const int val) {
    listPushBack(stack->list, val);
}

int stackTop(Stack *stack) {
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
    delete stack->list;
}


bool stackTestModule() {
    bool result = true;
    if (!stackTestCreateErase()) {
        printf("Stack module: Create\\Erase test failed\n");
        result = false;
    }
    if (!stackTestPushPop()) {
        printf("Stack module: Push\\Pop test failed\n");
        result = false;
    }
    if (!stackTestCheckEmpty()) {
        printf("Stack module: IsEmpty test failed\n");
        result = false;
    }
    return result;
}

bool stackTestCreateErase() {
    Stack *temp = createStack();
    stackErase(temp);
    return true;
}

bool stackTestPushPop() {
    Stack *temp = createStack();
    for (int i = 0; i < 10; i++)
        stackPush(temp, i);
    for (int i = 9; i >= 0; i--) {
        if (stackTop(temp) != i) {
            return false;
        }
        stackPop(temp);
    }
    stackErase(temp);
    return true;
}

bool stackTestCheckEmpty() {
    Stack *temp = createStack();
    if (!stackIsEmpty(temp))
        return false;
    stackPush(temp, 300);
    if (stackIsEmpty(temp))
        return false;
    stackPop(temp);
    if (!stackIsEmpty(temp))
        return false;
    stackErase(temp);
    return true;
}
