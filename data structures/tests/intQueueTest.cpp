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
    result &= (intQueueGetSize(temp) == 1);
    result &= (intQueuePeek(temp) == 1);
    intQueueDelete(temp);
    
    return result;
}

bool testPop() {
    bool result = true;
    
    IntQueue *temp = intQueueCreate();
    intQueuePush(temp, 1);
    intQueuePop(temp);
    result &= (intQueueGetSize(temp) == 0);
    intQueueDelete(temp);
    
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
