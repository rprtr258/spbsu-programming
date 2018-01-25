#include <stdio.h>
#include <string.h>
#include <ctype.h>
#include "../../../lib/intStack/intStack.h"

int eval(char *expr, const int exprLength) {
    IntStack *stack = intStackCreate();
    for (int i = 0; i < exprLength; i++) {
        if (isdigit(expr[i])) {
            intStackPush(stack, expr[i] - '0');
        } else {
            int operandA = 0;
            int operandB = 0;
            int temp = 0;
            operandB = intStackPeek(stack);
            intStackPop(stack);
            operandA = intStackPeek(stack);
            intStackPop(stack);
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
            intStackPush(stack, temp);
        }
    }
    
    int result = intStackPeek(stack);
    intStackDelete(stack);
    
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
