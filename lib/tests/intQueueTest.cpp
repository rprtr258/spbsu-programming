#include <stdio.h>
#include "../intQueue/intQueue.h"

bool testCreationDeletion() {
    bool result = true;
    
    IntQueue *temp1 = intQueueCreate();
    intQueueDelete(temp1);
    
    IntQueue *temp2 = intQueueCreate();
    intQueuePush(temp2, 1);
    intQueueDelete(temp2);
    
    return result;
}

bool testCreationEraseDeletion() {
    bool result = true;
    
    IntQueue *temp1 = intQueueCreate();
    intQueueErase(temp1);
    result &= (intQueueGetSize(temp1) == 0);
    intQueueDelete(temp1);
    
    IntQueue *temp2 = intQueueCreate();
    intQueuePush(temp2, 1);
    intQueueErase(temp2);
    result &= (intQueueGetSize(temp2) == 0);
    intQueueDelete(temp2);
    
    return result;
}

bool testPush() {
    bool result = true;
    
    IntQueue *temp = intQueueCreate();
    intQueuePush(temp, 1);
    intQueuePush(temp, 2);
    result &= (intQueueGetSize(temp) == 2);
    result &= (intQueuePeek(temp) == 1);
    intQueueDelete(temp);
    
    return result;
}

bool testPop() {
    bool result = true;
    
    IntQueue *temp = intQueueCreate();
    intQueuePush(temp, 1);
    intQueuePush(temp, 2);
    intQueuePop(temp);
    result &= (intQueueGetSize(temp) == 1);
    result &= (intQueuePeek(temp) == 2);
    intQueueDelete(temp);
    
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
