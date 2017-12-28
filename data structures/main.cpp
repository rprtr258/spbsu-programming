#include <stdio.h>
#include "list/intList.h"

/*
 * returns true if all tests passed, false otherwise
 */
bool runTests(bool const printDebug = false) {
    if (!testIntLinkedList(printDebug)) {
        printf("\nIntLinkedList tests failed\n");
        return false;
    }
}

int main() {
    if (!runTests(true))
        return 0;
    
    return 0;
}
