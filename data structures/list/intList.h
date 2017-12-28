#pragma once

struct Node;

struct IntLinkedList {
    Node *head = nullptr;
    Node *tail = nullptr;
    int size = 0;
};

IntLinkedList* intListCreate();
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

int intListFind(IntLinkedList *list, int const value);

void intListPrint(IntLinkedList *list);
void intListPrintSiblings(IntLinkedList *list, int const index);

bool testIntLinkedList(bool const printDebug = true);
