#include <stdio.h>
#include "../heap/heap.h"

bool testCreationDeletion() {
    bool result = true;
    
    Heap *temp1 = heapCreate();
    heapDelete(temp1);
    
    Heap *temp2 = heapCreate();
    heapPush(temp2, 1);
    heapDelete(temp2);
    
    return result;
}

bool testArray() {
    bool result = true;
    
    int array[] = {5, 2, 1, 6};
    Heap *temp = heapBuildOnArray(array, 4);
    heapPush(temp, 7);
    result &= (heapPop(temp) == 1);
    result &= (heapPop(temp) == 2);
    result &= (heapPop(temp) == 5);
    result &= (heapPop(temp) == 6);
    result &= (heapPop(temp) == 7);
    
    heapDelete(temp);
    
    return result;
}

int main() {
    char passed[] = "\x1b[32mpassed\x1b[0m";
    char failed[] = "\x1b[31mfailed\x1b[0m";
    
    printf("CreationDeletion test %s!\n", testCreationDeletion() ? passed : failed);
    printf("Array test %s!\n", testArray() ? passed : failed);
    
    return 0;
}
