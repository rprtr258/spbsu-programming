#include <stdio.h>
#include "../string/string.h"
#include "../stringList/stringList.h"

bool testCreationDeletion() {
    bool result = true;
    
    StringLinkedList *temp1 = stringListCreate();
    stringListDelete(temp1);
    
    StringLinkedList *temp2 = stringListCreate();
    String *one = stringCreate("1");
    stringListInsertAtBegin(temp2, one);
    stringListDelete(temp2);
    stringDelete(one);
    
    return result;
}

bool testCreationEraseDeletion() {
    bool result = true;
    
    StringLinkedList *temp1 = stringListCreate();
    stringListErase(temp1);
    result &= (temp1->size == 0);
    stringListDelete(temp1);
    
    StringLinkedList *temp2 = stringListCreate();
    String *one = stringCreate("1");
    stringListInsertAtBegin(temp2, one);
    stringListErase(temp2);
    result &= (temp2->size == 0);
    stringDelete(one);
    stringListDelete(temp2);
    
    return result;
}

bool testPrintEmpty() {
    bool result = true;
    
    printf("Empty list: ");
    StringLinkedList *temp = stringListCreate();
    stringListPrint(temp);
    stringListDelete(temp);
    
    return result;
}

bool testPrintNonEmpty() {
    bool result = true;
    
    printf("Non-empty list: ");
    StringLinkedList *temp = stringListCreate();
    String *one = stringCreate("100");
    String *two = stringCreate("500");
    String *three = stringCreate("300");
    stringListInsertAtEnd(temp, one);
    stringListInsertAtEnd(temp, two);
    stringListInsertAtEnd(temp, three);
    stringListPrint(temp);
    stringListDelete(temp);
    stringDelete(one);
    stringDelete(two);
    stringDelete(three);
    
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
    
    StringLinkedList *temp = stringListCreate();
    String *one = stringCreate("1");
    String *two = stringCreate("2");
    String *three = stringCreate("3");
    stringListInsertAtBegin(temp, three);
    stringListInsertAtBegin(temp, two);
    stringListInsertAtBegin(temp, one);
    String *oneMy = stringListPeekBegin(temp);
    String *twoMy = stringListPeekIndex(temp, 1);
    String *threeMy = stringListPeekEnd(temp);
    result &= stringAreEqual(oneMy, one);
    result &= stringAreEqual(twoMy, two);
    result &= stringAreEqual(threeMy, three);
    stringDelete(oneMy);
    stringDelete(twoMy);
    stringDelete(threeMy);
    stringDelete(one);
    stringDelete(two);
    stringDelete(three);
    stringListDelete(temp);
    
    return result;
}

bool testInsertAtIndex() {
    bool result = true;
    
    StringLinkedList *temp = stringListCreate();
    String *one = stringCreate("1");
    String *two = stringCreate("2");
    String *three = stringCreate("3");
    stringListInsertAtIndex(temp, one, 0);
    stringListInsertAtIndex(temp, three, 1);
    stringListInsertAtIndex(temp, two, 1);
    String *oneMy = stringListPeekBegin(temp);
    String *twoMy = stringListPeekIndex(temp, 1);
    String *threeMy = stringListPeekEnd(temp);
    result &= stringAreEqual(oneMy, one);
    result &= stringAreEqual(twoMy, two);
    result &= stringAreEqual(threeMy, three);
    stringDelete(oneMy);
    stringDelete(twoMy);
    stringDelete(threeMy);
    stringDelete(one);
    stringDelete(two);
    stringDelete(three);
    stringListDelete(temp);
    
    return result;
}

bool testInsertAtEnd() {
    bool result = true;
    
    StringLinkedList *temp = stringListCreate();
    String *one = stringCreate("1");
    String *two = stringCreate("2");
    String *three = stringCreate("3");
    stringListInsertAtEnd(temp, one);
    stringListInsertAtEnd(temp, two);
    stringListInsertAtEnd(temp, three);
    String *oneMy = stringListPeekBegin(temp);
    String *twoMy = stringListPeekIndex(temp, 1);
    String *threeMy = stringListPeekEnd(temp);
    result &= stringAreEqual(oneMy, one);
    result &= stringAreEqual(twoMy, two);
    result &= stringAreEqual(threeMy, three);
    stringDelete(oneMy);
    stringDelete(twoMy);
    stringDelete(threeMy);
    stringDelete(one);
    stringDelete(two);
    stringDelete(three);
    stringListDelete(temp);
    
    return result;
}

bool testFind() {
    bool result = true;
    
    StringLinkedList *temp = stringListCreate();
    String *one = stringCreate("1");
    String *two = stringCreate("2");
    String *three = stringCreate("3");
    stringListInsertAtEnd(temp, one);
    stringListInsertAtEnd(temp, two);
    stringListInsertAtEnd(temp, three);
    result &= (stringListFind(temp, one) == 0);
    result &= (stringListFind(temp, two) == 1);
    result &= (stringListFind(temp, three) == 2);
    stringDelete(one);
    stringDelete(two);
    stringDelete(three);
    stringListDelete(temp);
    
    return result;
}

bool testSorted() {
    bool result = true;
    
    StringLinkedList *temp = stringListCreate();
    String *one = stringCreate("1");
    String *two = stringCreate("2");
    String *three = stringCreate("3");
    stringListInsertAtEnd(temp, one);
    stringListInsertAtEnd(temp, two);
    stringListInsertAtEnd(temp, three);
    result &= stringListIsSorted(temp);
    stringDelete(one);
    stringDelete(two);
    stringDelete(three);
    stringListDelete(temp);
    
    return result;
}

bool testNonSorted() {
    bool result = true;
    
    StringLinkedList *temp = stringListCreate();
    String *two = stringCreate("2");
    String *three = stringCreate("3");
    stringListInsertAtEnd(temp, three);
    stringListInsertAtEnd(temp, two);
    stringListInsertAtEnd(temp, three);
    result &= (!stringListIsSorted(temp));
    stringDelete(two);
    stringDelete(three);
    stringListDelete(temp);
    
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
    
    StringLinkedList *temp1 = stringListCreate(); // [1, 3, 5, 7]
    String *one = stringCreate("1");
    String *two = stringCreate("3");
    String *three = stringCreate("5");
    String *four = stringCreate("7");
    stringListInsertAtEnd(temp1, one);
    stringListInsertAtEnd(temp1, two);
    stringListInsertAtEnd(temp1, three);
    stringListInsertAtEnd(temp1, four);
    stringErase(one);
    stringErase(two);
    stringErase(three);
    stringErase(four);
    
    StringLinkedList *temp2 = stringListCreate(); // [2, 4, 6]
    one = stringCreate("2");
    two = stringCreate("4");
    three = stringCreate("6");
    stringListInsertAtEnd(temp2, one);
    stringListInsertAtEnd(temp2, two);
    stringListInsertAtEnd(temp2, three);
    
    StringLinkedList *merged = stringListMergeSorted(temp1, temp2);
    result &= stringListIsSorted(merged);
    result &= (merged->size == temp1->size + temp2->size);
    
    stringDelete(one);
    stringDelete(two);
    stringDelete(three);
    stringDelete(four);
    stringListDelete(temp1);
    stringListDelete(temp2);
    stringListDelete(merged);
    
    return result;
}

bool testCopy() {
    bool result = true;
    
    String *one = stringCreate("1");
    String *two = stringCreate("2");
    String *three = stringCreate("3");
    
    StringLinkedList *temp1 = stringListCreate();
    stringListInsertAtEnd(temp1, one);
    stringListInsertAtEnd(temp1, two);
    stringListInsertAtEnd(temp1, three);
    
    StringLinkedList *temp2 = stringListCopy(temp1);
    result &= (stringListFind(temp2, one) == 0);
    result &= (stringListFind(temp2, two) == 1);
    result &= (stringListFind(temp2, three) == 2);
    
    stringDelete(one);
    stringDelete(two);
    stringDelete(three);
    stringListDelete(temp1);
    stringListDelete(temp2);
    
    return result;
}

bool testMergeSort() {
    bool result = true;
    
    StringLinkedList *temp = stringListCreate();
    String *one = stringCreate("2");
    String *two = stringCreate("1");
    String *three = stringCreate("4");
    String *four = stringCreate("3");
    stringListInsertAtEnd(temp, one);
    stringListInsertAtEnd(temp, two);
    stringListInsertAtEnd(temp, three);
    stringListInsertAtEnd(temp, four);
    
    stringListSort(temp);
    result &= (stringListFind(temp, two) == 0);
    result &= (stringListFind(temp, one) == 1);
    result &= (stringListFind(temp, four) == 2);
    result &= (stringListFind(temp, three) == 3);
    
    stringDelete(one);
    stringDelete(two);
    stringDelete(three);
    stringDelete(four);
    stringListDelete(temp);
    
    return result;
}

bool testGetArray() {
    bool result = true;
    
    StringLinkedList *temp = stringListCreate();
    String *one = stringCreate("0");
    String *two = stringCreate("1");
    String *three = stringCreate("2");
    String *four = stringCreate("3");
    stringListInsertAtEnd(temp, one);
    stringListInsertAtEnd(temp, two);
    stringListInsertAtEnd(temp, three);
    stringListInsertAtEnd(temp, four);
    String **array = stringListGetAsArray(temp);
    
    result &= stringAreEqual(one, array[0]);
    result &= stringAreEqual(two, array[1]);
    result &= stringAreEqual(three, array[2]);
    result &= stringAreEqual(four, array[3]);
    
    for (int unsigned i = 0; i < temp->size; i++)
        stringDelete(array[i]);
    delete[] array;
    stringListDelete(temp);
    stringDelete(one);
    stringDelete(two);
    stringDelete(three);
    stringDelete(four);
    
    return result;
}

int main() {
    char passed[] = "\x1b[32mpassed\x1b[0m";
    char failed[] = "\x1b[31mfailed\x1b[0m";
    printf("CreationDeletion test %s!\n", testCreationDeletion() ? passed : failed);
    printf("InsertAtBegin test %s!\n", testInsertAtBegin() ? passed : failed);
    printf("InsertAtIndex test %s!\n", testInsertAtIndex() ? passed : failed);
    printf("InsertAtEnd test %s!\n", testInsertAtEnd() ? passed : failed);
    printf("CreationEraseDeletion test %s!\n", testCreationEraseDeletion() ? passed : failed);
    printf("Print test %s!\n", testPrint() ? passed : failed);
    printf("Find test %s!\n", testFind() ? passed : failed);
    printf("IsSorted test %s!\n", testIsSorted() ? passed : failed);
    printf("Copy test %s!\n", testCopy() ? passed : failed);
    printf("MergeSorted test %s!\n", testMergeSorted() ? passed : failed);
    printf("MergeSort test %s!\n", testMergeSort() ? passed : failed);
    printf("GetArray test %s!\n", testGetArray() ? passed : failed);
    
    return 0;
}
