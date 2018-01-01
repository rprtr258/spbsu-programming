#include <stdio.h>
#include "intList/intList.h"
#include "string/string.h"

/*
 * returns true if all tests passed, false otherwise
 */
bool runTests(bool const printDebug = false) {
    printf(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n");
    printf("String tests have started\n");
    printf("===============================\n");
    if (!testString(printDebug)) {
        printf("\nString tests failed\n");
        return false;
    }
    printf("===============================\n");
    printf("String tests have passed\n");
    printf("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n\n");
    
    return true;
}

int main() {
    if (!runTests(true))
        return 0;
    
    return 0;
}
