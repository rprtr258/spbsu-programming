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
    stringListInsertAtBegin(temp2, "1");
    stringListErase(temp2);
    result &= (temp2->size == 0);
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
    stringListInsertAtEnd(temp, "lol");
    stringListInsertAtEnd(temp, "kek");
    stringListInsertAtEnd(temp, "cheburek");
    stringListPrint(temp);
    stringListDelete(temp);
    
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
    stringListInsertAtEnd(temp, "1");
    stringListInsertAtEnd(temp, "2");
    stringListInsertAtEnd(temp, "3");
    result &= (stringListFind(temp, one) == 0);
    result &= (stringListFind(temp, two) == 1);
    result &= (stringListFind(temp, three) == 2);
    stringListDelete(temp);
    stringDelete(one);
    stringDelete(two);
    stringDelete(three);
    
    return result;
}

bool testSorted() {
    bool result = true;
    
    StringLinkedList *temp = stringListCreate();
    stringListInsertAtEnd(temp, "");
    stringListInsertAtEnd(temp, "bc");
    stringListInsertAtEnd(temp, "d");
    result &= stringListIsSorted(temp);
    stringListDelete(temp);
    
    return result;
}

bool testNonSorted() {
    bool result = true;
    
    StringLinkedList *temp = stringListCreate();
    stringListInsertAtEnd(temp, "b");
    stringListInsertAtEnd(temp, "ak47");
    stringListInsertAtEnd(temp, "azino");
    result &= (!stringListIsSorted(temp));
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
    stringListInsertAtEnd(temp1, "1");
    stringListInsertAtEnd(temp1, "3");
    stringListInsertAtEnd(temp1, "5");
    stringListInsertAtEnd(temp1, "7");
    
    StringLinkedList *temp2 = stringListCreate(); // [2, 4, 6]
    stringListInsertAtEnd(temp2, "2");
    stringListInsertAtEnd(temp2, "4");
    stringListInsertAtEnd(temp2, "6");
    
    StringLinkedList *merged = stringListMergeSorted(temp1, temp2);
    result &= stringListIsSorted(merged);
    result &= (merged->size == temp1->size + temp2->size);
    
    stringListDelete(temp1);
    stringListDelete(temp2);
    stringListDelete(merged);
    
    return result;
}

bool testCopy() {
    bool result = true;
    
    StringLinkedList *temp1 = stringListCreate();
    stringListInsertAtEnd(temp1, "1");
    stringListInsertAtEnd(temp1, "2");
    stringListInsertAtEnd(temp1, "ololo");
    
    StringLinkedList *temp2 = stringListCopy(temp1);
    result &= (stringListFind(temp2, "1") == 0);
    result &= (stringListFind(temp2, "2") == 1);
    result &= (stringListFind(temp2, "ololo") == 2);
    
    stringListDelete(temp1);
    stringListDelete(temp2);
    
    return result;
}

bool testMergeSort() {
    bool result = true;
    
    StringLinkedList *temp = stringListCreate();
    stringListInsertAtEnd(temp, "2");
    stringListInsertAtEnd(temp, "1");
    stringListInsertAtEnd(temp, "4");
    stringListInsertAtEnd(temp, "3");
    
    stringListSort(temp);
    result &= (stringListFind(temp, "1") == 0);
    result &= (stringListFind(temp, "2") == 1);
    result &= (stringListFind(temp, "3") == 2);
    result &= (stringListFind(temp, "4") == 3);
    
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
