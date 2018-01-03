#include <stdio.h>
#include "../intStack/intStack.h"

bool testCreationDeletion() {
    bool result = true;
    
    IntStack *temp1 = intStackCreate();
    intStackDelete(temp1);
    
    IntStack *temp2 = intStackCreate();
    intStackPush(temp2, 1);
    intStackDelete(temp2);
    
    return result;
}

bool testCreationEraseDeletion() {
    bool result = true;
    
    IntStack *temp1 = intStackCreate();
    intStackErase(temp1);
    result &= (intStackGetSize(temp1) == 0);
    intStackDelete(temp1);
    
    IntStack *temp2 = intStackCreate();
    intStackPush(temp2, 1);
    intStackErase(temp2);
    result &= (intStackGetSize(temp2) == 0);
    intStackDelete(temp2);
    
    return result;
}

bool testPush() {
    bool result = true;
    
    IntStack *temp = intStackCreate();
    intStackPush(temp, 1);
    intStackPush(temp, 2);
    result &= (intStackGetSize(temp) == 2);
    result &= (intStackPeek(temp) == 2);
    intStackDelete(temp);
    
    return result;
}

bool testPop() {
    bool result = true;
    
    IntStack *temp = intStackCreate();
    intStackPush(temp, 1);
    intStackPush(temp, 2);
    intStackPop(temp);
    result &= (intStackGetSize(temp) == 1);
    result &= (intStackPeek(temp) == 1);
    intStackDelete(temp);
    
    return result;
}

int main() {
    char passed[] = "\x1b[32mpassed\x1b[0m";
    char failed[] = "\x1b[31mfailed\x1b[0m";
    
    printf("CreationDeletion test %s!\n", testCreationDeletion() ? passed : failed);
    printf("CreationEraseDeletion test %s!\n", testCreationEraseDeletion() ? passed : failed);
    printf("Push test %s!\n", testPush() ? passed : failed);
    printf("Pop test %s!\n", testPop() ? passed : failed);
    
    return 0;
}
