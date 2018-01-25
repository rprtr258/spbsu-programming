#include <stdio.h>
#include "tests.h"

bool test() {
    if (!testParse()) {
        printf("Parse test failed\n");
        return false;
    }
    return true;
}
