#include <stdio.h>
#include <string.h>
#include <ctype.h>
#include "../eval.h"
#include "../../utils/stack.h"

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
