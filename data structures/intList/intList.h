#pragma once

struct Node;

struct IntLinkedList {
    Node *head = nullptr;
    Node *tail = nullptr;
    int size = 0;
};

IntLinkedList* intListCreate();
IntLinkedList* intListCopy(IntLinkedList *other);
IntLinkedList* intListMergeSorted(IntLinkedList *first, IntLinkedList *second);
void intListErase(IntLinkedList *list);
void intListDelete(IntLinkedList *&list);

void intListInsertAtEnd(IntLinkedList *list, int const value);
void intListInsertAtBegin(IntLinkedList *list, int const value);
void intListInsertAtIndex(IntLinkedList *list, int const value, int const index);

int intListPeekBegin(IntLinkedList *list);
int intListPeekEnd(IntLinkedList *list);
int intListPeekIndex(IntLinkedList *list, int const index);

void intListDeleteBegin(IntLinkedList *list);
void intListDeleteEnd(IntLinkedList *list);
void intListDeleteIndex(IntLinkedList *list, int const index);

void intListLeaveUniques(IntLinkedList *list);
void intListSort(IntLinkedList *&list);

int intListFind(IntLinkedList *list, int const value);
bool intListIsSorted(IntLinkedList *list);
int* intListGetAsArray(IntLinkedList *list);

void intListPrint(IntLinkedList *list);
void intListPrintSiblings(IntLinkedList *list, int const index);
