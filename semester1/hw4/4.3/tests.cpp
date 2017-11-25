#include <stdio.h>
#include "tests.h"

bool test() {
    if (!stackTestModule() || !listTestModule())
        return false;
    if (!testParse()) {
        printf("Parse test failed\n");
        return false;
    }
    return true;
}
