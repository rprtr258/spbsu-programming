#include <stdio.h>
#include "list/list.h"

/*
 * returns true if all tests passed, false otherwise
 */
bool runTests() {
    if (!testIntLinkedList()) {
        printf("\nIntLinkedList tests failed\n");
        return false;
    }
}

int main() {
    if (!runTests())
        return 0;
    
    return 0;
}
