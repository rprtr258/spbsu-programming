#include <stdio.h>
#include <string.h>
#include <ctype.h>
#include "polynom.h"

// TODO: move all shit to module

int main() {
    printf("Beautiful polynom\n");
    
    printf("Write coefficients of polynom as array \"an ... a1 a0\" in one line,\n");
    printf("where pol(x) = an * x ^ n + ... + a1 * x + a0\n");
    char str[1000];
    gets(str);
    
    char ansRow1[1000];
    char ansRow2[1000];
    beautify(str, ansRow1, ansRow2);
    
    printf("Beautiful polynome:\n");
    printf("%s\n", ansRow1);
    printf("%s", ansRow2);
    return 0;
}