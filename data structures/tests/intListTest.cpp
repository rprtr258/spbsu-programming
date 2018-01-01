#include <stdio.h>
#include "../intList/intList.h"

bool testCreationDeletion() {
    bool result = true;
    
    IntLinkedList *temp1 = intListCreate();
    intListDelete(temp1);
    
    IntLinkedList *temp2 = intListCreate();
    intListInsertAtBegin(temp2, 1);
    intListDelete(temp2);
    
    return result;
}

bool testCreationEraseDeletion() {
    bool result = true;
    
    IntLinkedList *temp1 = intListCreate();
    intListErase(temp1);
    result &= (temp1->size == 0);
    intListDelete(temp1);
    
    IntLinkedList *temp2 = intListCreate();
    intListInsertAtBegin(temp2, 1);
    intListErase(temp2);
    result &= (temp2->size == 0);
    intListDelete(temp2);
    
    return result;
}

bool testPrintEmpty() {
    bool result = true;
    
    printf("Empty list: ");
    IntLinkedList *temp = intListCreate();
    intListPrint(temp);
    intListDelete(temp);
    
    return result;
}

bool testPrintNonEmpty() {
    bool result = true;
    
    printf("Non-empty list: ");
    IntLinkedList *temp = intListCreate();
    intListInsertAtEnd(temp, 100);
    intListInsertAtEnd(temp, 500);
    intListInsertAtEnd(temp, 300);
    intListPrint(temp);
    intListDelete(temp);
    
    return result;
}

bool testPrint() {
    bool result = true;
    
    result &= testPrintEmpty();
    result &= testPrintNonEmpty();
    
    return result;
}

bool testInsertAtBegin() {
    bool result = true;
    
    IntLinkedList *temp = intListCreate();
    intListInsertAtBegin(temp, 3);
    intListInsertAtBegin(temp, 2);
    intListInsertAtBegin(temp, 1);
    result &= (intListPeekBegin(temp) == 1);
    result &= (intListPeekIndex(temp, 1) == 2);
    result &= (intListPeekEnd(temp) == 3);
    intListDelete(temp);
    
    return result;
}

bool testInsertAtIndex() {
    bool result = true;
    
    IntLinkedList *temp = intListCreate();
    intListInsertAtIndex(temp, 1, 0);
    intListInsertAtIndex(temp, 3, 1);
    intListInsertAtIndex(temp, 2, 1);
    result &= (intListPeekBegin(temp) == 1);
    result &= (intListPeekIndex(temp, 1) == 2);
    result &= (intListPeekEnd(temp) == 3);
    intListDelete(temp);
    
    return result;
}

bool testInsertAtEnd() {
    bool result = true;
    
    IntLinkedList *temp = intListCreate();
    intListInsertAtEnd(temp, 1);
    intListInsertAtEnd(temp, 2);
    intListInsertAtEnd(temp, 3);
    result &= (intListPeekBegin(temp) == 1);
    result &= (intListPeekIndex(temp, 1) == 2);
    result &= (intListPeekEnd(temp) == 3);
    intListDelete(temp);
    
    return result;
}

bool testFind() {
    bool result = true;
    
    IntLinkedList *temp = intListCreate();
    intListInsertAtEnd(temp, 1);
    intListInsertAtEnd(temp, 2);
    intListInsertAtEnd(temp, 3);
    intListPrint(temp);
    result &= (intListFind(temp, 1) == 0);
    result &= (intListFind(temp, 2) == 1);
    result &= (intListFind(temp, 3) == 2);
    intListDelete(temp);
    
    return result;
}

bool testSorted() {
    bool result = true;
    
    IntLinkedList *temp = intListCreate();
    intListInsertAtEnd(temp, 1);
    intListInsertAtEnd(temp, 2);
    intListInsertAtEnd(temp, 3);
    result &= intListIsSorted(temp);
    intListDelete(temp);
    
    return result;
}

bool testNonSorted() {
    bool result = true;
    
    IntLinkedList *temp = intListCreate();
    intListInsertAtEnd(temp, 3);
    intListInsertAtEnd(temp, 2);
    intListInsertAtEnd(temp, 3);
    result &= (!intListIsSorted(temp));
    intListDelete(temp);
    
    return result;
}

bool testIsSorted() {
    bool result = true;
    
    result &= testSorted();
    result &= testNonSorted();
    
    return result;
}

int main() {
    if (!testCreationDeletion()) {
        printf("CreationDeletion test failed!\n");
        return 0;
    }
    if (!testInsertAtBegin()) {
        printf("InsertAtBegin test failed!\n");
        return 0;
    }
    if (!testInsertAtIndex()) {
        printf("InsertAtIndex test failed!\n");
        return 0;
    }
    if (!testInsertAtEnd()) {
        printf("InsertAtEnd test failed!\n");
        return 0;
    }
    if (!testCreationEraseDeletion()) {
        printf("CreationEraseDeletion test failed!\n");
        return 0;
    }
    if (!testPrint()) {
        printf("Print test failed!\n");
        return 0;
    }
    if (!testFind()) {
        printf("Find test failed!\n");
        return 0;
    }
    if (!testIsSorted()) {
        printf("IsSorted test failed!\n");
        return 0;
    }
    return 0;
}
