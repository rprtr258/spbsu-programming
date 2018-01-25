#include <stdio.h>
#include <string.h>
#include "parse.h"
#include "tests.h"

int main() {
//    if (!test())
//        return 0;
    printf("Program to transform infix notion into postfix\n");
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
