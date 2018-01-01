#include <stdio.h>
#include "../intList/intList.h"

bool printDebug = true;

int main() {
    bool result = true;
    
    IntLinkedList *temp = intListCreate();
    
    if (printDebug) {
        printf("Empty list: ");
        intListPrint(temp);
    }
    
    intListInsertAtBegin(temp, 1);
    if (printDebug) {
        printf("Insert \'1\' at begin: ");
        intListPrint(temp);
    }
    
    intListInsertAtEnd(temp, 3);
    if (printDebug) {
        printf("Insert \'3\' at end: ");
        intListPrint(temp);
    }
    
    int j = intListFind(temp, 3);
    intListInsertAtIndex(temp, 2, j);
    if (printDebug) {
        printf("Insert 2 at position %d: ", j);
        intListPrint(temp);
    }
    
    result &= (intListPeekBegin(temp) == 1);
    result &= (intListPeekIndex(temp, 1) == 2);
    result &= (intListPeekEnd(temp) == 3);
    
    intListInsertAtEnd(temp, 1);
    
    j = intListFind(temp, 1);
    if (printDebug) {
        printf("Siblings of 1:\n");
        intListPrintSiblings(temp, j);
        intListPrint(temp);
    }
    
    bool isSorted1 = intListIsSorted(temp);
    result &= (!isSorted1);
    if (printDebug)
        printf("Is sorted: %s\n", isSorted1 ? "YES" : "NO");
    
    intListLeaveUniques(temp);
    if (printDebug) {
        printf("Leave uniques:\n");
        intListPrint(temp);
    }
    
    bool isSorted2 = intListIsSorted(temp);
    result &= (isSorted2);
    if (printDebug)
        printf("Is sorted: %s\n", isSorted2 ? "YES" : "NO");
    
    intListDeleteIndex(temp, 1);
    if (printDebug) {
        printf("Delete element at index 1: ");
        intListPrint(temp);
    }
    
    intListDeleteBegin(temp);
    if (printDebug) {
        printf("Delete element at begin: ");
        intListPrint(temp);
    }
    
    intListDeleteEnd(temp);
    if (printDebug) {
        printf("Delete element at end: ");
        intListPrint(temp);
    }
    
    intListErase(temp);
    result &= (temp->size == 0);
    
    intListDelete(temp);
    return result;
}
