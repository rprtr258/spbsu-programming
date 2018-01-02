#include <stdio.h>
#include "intList/intList.h"
#include "string/string.h"

bool testString() {
    String* epic = stringCreate("Hello, ");
    String* win = stringCreate("World!");
    String* wut = stringConcate(epic, win);
    char *tmp = stringGetRaw(wut);
    
    String *substr1 = stringGetSubstring(wut, 3, 2);
    String *substr2 = stringGetSubstring(substr1, 0, 1);
    String *substr = stringConcate(substr1, substr2);
    
    printf("%s\n", tmp);
    printf("%s\n", substr->data);

    stringDelete(epic);
    stringDelete(win);
    stringDelete(wut);
    stringDelete(substr1);
    stringDelete(substr2);
    stringDelete(substr);
    delete[] tmp;
    return true;
}

void runTests() {
    printf(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n");
    printf("String tests have started\n");
    printf("===============================\n");
    if (!testString()) {
        printf("\nString tests failed\n");
    }
    printf("===============================\n");
    printf("String tests have passed\n");
    printf("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n\n");
}

int main() {
    runTests();
    
    return 0;
}
