#pragma once
#include "list.h"

struct Stack;

Stack* createStack();

void stackPush(Stack *stack, const char val);
char stackTop(Stack *stack);
void stackPop(Stack *stack);

bool stackIsEmpty(Stack *stack);

void stackErase(Stack *stack);
