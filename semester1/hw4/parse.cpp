#include <string.h>
#include <ctype.h>
#include "parse.h"
#include "../utils/stack.h"

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
