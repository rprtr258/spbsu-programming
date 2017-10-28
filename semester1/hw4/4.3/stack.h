#pragma once
#include "list.h"

struct Stack {
    LinkedList *list = createList();
};

Stack* createStack();

void stackPush(Stack *stack, const char val);
char stackTop(Stack *stack);
void stackPop(Stack *stack);

bool stackIsEmpty(Stack *stack);

void stackErase(Stack *stack);

bool stackTestModule();
bool stackTestCreateErase();
bool stackTestPushPop();
bool stackTestCheckEmpty();
