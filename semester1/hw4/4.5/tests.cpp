#include <stdio.h>
#include <string.h>
#include "../parse.h"
#include "../eval.h"
#include "tests.h"

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
