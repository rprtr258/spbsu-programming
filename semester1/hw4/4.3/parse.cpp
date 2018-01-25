#include <string.h>
#include <ctype.h>
#include "parse.h"
#include "../../../lib/intStack/intStack.h"

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
    IntStack *stack = intStackCreate();
    for (int i = 0; i < exprLength; i++) {
        if (isdigit(expr[i])) {
            out[ptr] = expr[i];
            ptr++;
        } else if (expr[i] == '(') {
            intStackPush(stack, '(');
        } else if (expr[i] == ')') {
            while (intStackPeek(stack) != '(') {
                out[ptr] = intStackPeek(stack);
                ptr++;
                intStackPop(stack);
            }
            intStackPop(stack); // remove '('
        } else {
            while (!intStackIsEmpty(stack) && isOperator(intStackPeek(stack)) && priority(expr[i]) <= priority(intStackPeek(stack))) {
                out[ptr] = intStackPeek(stack);
                ptr++;
                intStackPop(stack);
            }
            intStackPush(stack, expr[i]);
        }
    }
    while (!intStackIsEmpty(stack)) {
        out[ptr] = intStackPeek(stack);
        ptr++;
        intStackPop(stack);
    }
    out[ptr] = '\0';
    
    intStackDelete(stack);
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
