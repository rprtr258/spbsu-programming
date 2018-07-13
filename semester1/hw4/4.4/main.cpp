#include <stdio.h>
#include <string.h>
#include <ctype.h>
#include "../../utils/stack.h"

int eval(char *expr, const int exprLength) {
    Stack *stack = createStack();
    for (int i = 0; i < exprLength; i++) {
        if (isdigit(expr[i])) {
            stackPush(stack, expr[i] - '0');
        } else {
            int operandA = 0;
            int operandB = 0;
            int temp = 0;
            operandB = stackTop(stack);
            stackPop(stack);
            operandA = stackTop(stack);
            stackPop(stack);
            switch (expr[i]) {
                case '+': {
                    temp = operandA + operandB;
                    break;
                }
                case '-': {
                    temp = operandA - operandB;
                    break;
                }
                case '*': {
                    temp = operandA * operandB;
                    break;
                }
                case '/': {
                    temp = operandA / operandB;
                    break;
                }
            }
            stackPush(stack, temp);
        }
    }
    
    int result = stackTop(stack);
    stackErase(stack);
    delete stack;
    
    return result;
}

bool testEval() {
    char temp1[] = "222*+";
    if (eval(temp1, strlen(temp1)) != 6)
        return false;
    char temp2[] = "222+*";
    if (eval(temp2, strlen(temp2)) != 8)
        return false;
    return true;
}

bool test() {
    if (!stackTestModule() || !listTestModule())
        return false;
    if (!testEval()) {
        printf("Eval test failed\n");
        return false;
    }
    return true;
}

int main() {
//    if (!test())
//        return 0;
    printf("Program to calc expression in postfix notion\n");
    char expr[1000];
    gets(expr);
    int exprLength = strlen(expr);
    
    int result = eval(expr, exprLength);
    
    printf("Result: %d\n", result);
    return 0;
}
