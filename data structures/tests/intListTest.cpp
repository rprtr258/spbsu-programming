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

bool testMergeSorted() {
    bool result = true;
    
    IntLinkedList *temp1 = intListCreate(); // [1, 3, 5, 7]
    intListInsertAtEnd(temp1, 1);
    intListInsertAtEnd(temp1, 3);
    intListInsertAtEnd(temp1, 5);
    intListInsertAtEnd(temp1, 7);
    
    IntLinkedList *temp2 = intListCreate(); // [2, 4, 6]
    intListInsertAtEnd(temp2, 2);
    intListInsertAtEnd(temp2, 4);
    intListInsertAtEnd(temp2, 6);
    
    IntLinkedList *merged = intListMergeSorted(temp1, temp2);
    result &= intListIsSorted(merged);
    result &= (merged->size == temp1->size + temp2->size);
    
    intListDelete(temp1);
    intListDelete(temp2);
    intListDelete(merged);
    
    return result;
}

bool testCopy() {
    bool result = true;
    
    IntLinkedList *temp1 = intListCreate();
    intListInsertAtEnd(temp1, 1);
    intListInsertAtEnd(temp1, 2);
    intListInsertAtEnd(temp1, 3);
    
    IntLinkedList *temp2 = intListCopy(temp1);
    result &= (intListFind(temp2, 1) == 0);
    result &= (intListFind(temp2, 2) == 1);
    result &= (intListFind(temp2, 3) == 2);
    
    intListDelete(temp1);
    intListDelete(temp2);
    
    return result;
}

bool testMergeSort() {
    bool result = true;
    
    IntLinkedList *temp = intListCreate();
    intListInsertAtEnd(temp, 2);
    intListInsertAtEnd(temp, 1);
    intListInsertAtEnd(temp, 4);
    intListInsertAtEnd(temp, 3);
    
    intListSort(temp);
    result &= (intListFind(temp, 1) == 0);
    result &= (intListFind(temp, 2) == 1);
    result &= (intListFind(temp, 3) == 2);
    result &= (intListFind(temp, 4) == 3);
    
    intListDelete(temp);
    
    return result;
}

bool testGetArray() {
    bool result = true;
    
    IntLinkedList *temp = intListCreate();
    intListInsertAtEnd(temp, 0);
    intListInsertAtEnd(temp, 1);
    intListInsertAtEnd(temp, 2);
    intListInsertAtEnd(temp, 3);
    int *array = intListGetAsArray(temp);
    for (int i = 0; i < temp->size; i++)
        result &= (array[i] == i);
    intListDelete(temp);
    delete[] array;
    
    return result;
}

int main() {
    if (!testCreationDeletion()) {
        printf("CreationDeletion test failed!\n");
    }
    if (!testInsertAtBegin()) {
        printf("InsertAtBegin test failed!\n");
    }
    if (!testInsertAtIndex()) {
        printf("InsertAtIndex test failed!\n");
    }
    if (!testInsertAtEnd()) {
        printf("InsertAtEnd test failed!\n");
    }
    if (!testCreationEraseDeletion()) {
        printf("CreationEraseDeletion test failed!\n");
    }
    if (!testPrint()) {
        printf("Print test failed!\n");
    }
    if (!testFind()) {
        printf("Find test failed!\n");
    }
    if (!testIsSorted()) {
        printf("IsSorted test failed!\n");
    }
    if (!testCopy()) {
        printf("Copy test failed!\n");
    }
    if (!testMergeSorted()) {
        printf("MergeSorted test failed!\n");
    }
    if (!testMergeSort()) {
        printf("MergeSort test failed!\n");
    }
    if (!testGetArray()) {
        printf("GetArray test failed!\n");
    }
    return 0;
}
