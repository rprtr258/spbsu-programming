#include <stdio.h>
#include <string.h>
#include <ctype.h>
#include "parse.h"
#include "tests.h"
#include "eval.h"

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
