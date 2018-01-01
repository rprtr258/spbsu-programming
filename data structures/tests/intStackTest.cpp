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
    result &= (intStackGetSize(temp) == 1);
    result &= (intStackPeek(temp) == 1);
    intStackDelete(temp);
    
    return result;
}

bool testPop() {
    bool result = true;
    
    IntStack *temp = intStackCreate();
    intStackPush(temp, 1);
    intStackPop(temp);
    result &= (intStackGetSize(temp) == 0);
    intStackDelete(temp);
    
    return result;
}

int main() {
    printf("Something\n");
    if (!testCreationDeletion()) {
        printf("CreationDeletion test failed!\n");
    }
    if (!testPush()) {
        printf("Push test failed!\n");
    }
    if (!testPop()) {
        printf("Pop test failed!\n");
    }
    if (!testCreationEraseDeletion()) {
        printf("CreationEraseDeletion test failed!\n");
    }
    return 0;
}
