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

bool test() {
    if (!stackTestModule() || !listTestModule())
        return false;
    if (!testParse()) {
        printf("Parse test failed\n");
        return false;
    }
    return true;
}

int main() {
//    if (!test())
//        return 0;
    printf("Program to transform infix notation into postfix\n");
    char expr[1000];
    gets(expr);
    int exprLength = strlen(expr);
    
    char result[1000];
    parse(expr, exprLength, result);
    
    int resultLength = strlen(result);
    printf("Result:\n");
    for (int i = 0; i < resultLength; i++) {
        printf("%c", result[i]);
    }
    return 0;
}
