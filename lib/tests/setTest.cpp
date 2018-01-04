#include <stdio.h>
#include "../set/set.h"

bool testCreationDeletion() {
    bool result = true;
    
    Set *set = setCreate();
    setDelete(set);
    
    Set *temp2 = setCreate();
    setInsert(temp2, 1);
    setDelete(temp2);
    
    return result;
}

bool testCreationEraseDeletion() {
    bool result = true;
    
    Set *set = setCreate();
    setErase(set);
    setDelete(set);
    
    Set *temp2 = setCreate();
    setInsert(temp2, 1);
    setErase(temp2);
    setDelete(temp2);
    
    return result;
}

bool testRandom() {
    bool result = true;
    
    Set *set = setCreate();
    setInsert(set, 1);
    setInsert(set, 0);
    setInsert(set, 1);
    setInsert(set, 2);
    result &= setContains(set, 0);
    result &= setContains(set, 1);
    result &= setContains(set, 2);
    result &= (!setContains(set, 3));
    setRemove(set, 1);
    result &= setContains(set, 1);
    setRemove(set, 1);
    result &= (!setContains(set, 1));
    setInsert(set, 3);
    setInsert(set, 4);
    setInsert(set, 555);
    setInsert(set, -2314);
    result &= setContains(set, 3);
    setErase(set);
    result &= (!setContains(set, 0));
    result &= (!setContains(set, 1));
    result &= (!setContains(set, 2));
    result &= (!setContains(set, 3));
    setDelete(set);
    
    return result;
}

int main() {
    char passed[] = "\x1b[32mpassed\x1b[0m";
    char failed[] = "\x1b[31mfailed\x1b[0m";
    
    printf("CreationDeletion test %s!\n", testCreationDeletion() ? passed : failed);
    printf("CreationEraseDeletion test %s!\n", testCreationEraseDeletion() ? passed : failed);
    printf("Random test %s!\n", testRandom() ? passed : failed);
    
    return 0;
}
