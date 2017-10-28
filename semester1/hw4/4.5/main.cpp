#include <stdio.h>
#include <string.h>
#include <ctype.h>
#include "stack.h"

bool isOperator(const char sym) {
    return (sym == '+' || sym == '-' || sym == '*' || sym == '/');
}

int priority(const char op) {
    if (op == '+' || op == '-')
        return 0;
    return 1;
}

void parse(char *expr, const int exprLength, char *out) {
    int ptr = 0;
    Stack *stack = createStack();
    for (int i = 0; i < exprLength; i++) {
        if (isdigit(expr[i])) {
            out[ptr] = expr[i];
            ptr++;
        } else if (expr[i] == '(') {
            stackPush(stack, '(');
        } else if (expr[i] == ')') {
            while (stackTop(stack) != '(') {
                out[ptr] = stackTop(stack);
                ptr++;
                stackPop(stack);
            }
            stackPop(stack); // remove '('
        } else {
            while (!stackIsEmpty(stack) && isOperator(stackTop(stack)) && priority(expr[i]) <= priority(stackTop(stack))) {
                out[ptr] = stackTop(stack);
                ptr++;
                stackPop(stack);
            }
            stackPush(stack, expr[i]);
        }
    }
    while (!stackIsEmpty(stack)) {
        out[ptr] = stackTop(stack);
        ptr++;
        stackPop(stack);
    }
    out[ptr] = '\0';
    
    stackErase(stack);
    delete stack;
}

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

bool testParse() {
    char res[100];
    char temp1[] = "2+2*2";
    parse(temp1, strlen(temp1), res);
    if (strcmp(res, "222*+"))
        return false;
    char temp2[] = "(2+2)*2";
    parse(temp2, strlen(temp2), res);
    if (strcmp(res, "22+2*"))
        return false;
    return true;
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

bool testCalc() {
    char res[100];
    char temp1[] = "2+2*2";
    parse(temp1, strlen(temp1), res);
    if (eval(res, strlen(res)) != 6)
        return false;
    char temp2[] = "(2+2)*2";
    parse(temp2, strlen(temp2), res);
    if (eval(res, strlen(res)) != 8)
        return false;
    char temp3[] = "(7*(9-8)+5)/6";
    parse(temp3, strlen(temp3), res);
    if (eval(res, strlen(res)) != 2)
        return false;
    return true;
}

bool test() {
    if (!stackTestModule() || !listTestModule())
        return false;
    if (!testParse()) {
        printf("Parse test failed\n");
        return false;
    }
    if (!testEval()) {
        printf("Eval test failed\n");
        return false;
    }
    if (!testCalc()) {
        printf("Calc test failed\n");
        return false;
    }
    return true;
}

int main() {
//    if (!test())
//        return 0;
    printf("CALCULATOR\n");
    char expr[1000];
    gets(expr);
    int exprLength = strlen(expr);
    
    char postfix[1000];
    parse(expr, exprLength, postfix);
    int postfixLength = strlen(postfix);
    int result = eval(postfix, postfixLength);
    
    printf("Result: %d", result);
    
    return 0;
}
