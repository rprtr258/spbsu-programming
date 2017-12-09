#include <stdio.h>
#include "exprtree.h"

bool testTree() {
    bool isOk = true;
    isOk &= testExpr("0", 0, "0");
    isOk &= testExpr("1", 1, "1");
    isOk &= testExpr("100500", 100500, "100500");
    isOk &= testExpr("-0", 0, "-0");
    isOk &= testExpr("-1", -1, "-1");
    isOk &= testExpr("-100500", -100500, "-100500");
    isOk &= testExpr("(+ 2 2)", 4, "2+2");
    isOk &= testExpr("(- 6 2)", 4, "6-2");
    isOk &= testExpr("(* 2 2)", 4, "2*2");
    isOk &= testExpr("(/ 8 2)", 4, "8/2");
    isOk &= testExpr("(* (+ 1 1) 2)", 4, "(1 + 1) * 2");
    return isOk;
}

int main() {
//    if (!testTree())
//        return 0;
    printf("This program calculates value represented as expression tree in \"file.txt\"\n");
    printf("Make sure you put correct tree here\n");
    FILE *myFile = fopen("file.txt", "r");
    
    char str[1000];
    fgets(str, sizeof(str), myFile);
    
    ExprTree *tree = parseFromString(str);
    calculate(tree);
    printInfix(tree);
    printf(" = %d\n", getResult(tree));
    
    erase(tree);
    delete tree;
    fclose(myFile);
    return 0;
}