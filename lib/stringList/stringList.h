#pragma once
#include "../string/string.h"

struct Node;
typedef int unsigned Index;

struct StringLinkedList {
    Node *head = nullptr;
    Node *tail = nullptr;
    Index size = 0;
};

StringLinkedList* stringListCreate();
StringLinkedList* stringListCopy(StringLinkedList *other);
StringLinkedList* stringListMergeSorted(StringLinkedList *first, StringLinkedList *second);
void stringListErase(StringLinkedList *list);
void stringListDelete(StringLinkedList *&list);

void stringListInsertAtEnd(StringLinkedList *list, String *value);
void stringListInsertAtEnd(StringLinkedList *list, char const *value);
void stringListInsertAtBegin(StringLinkedList *list, String *value);
void stringListInsertAtBegin(StringLinkedList *list, char const *value);
void stringListInsertAtIndex(StringLinkedList *list, String *value, Index const index);
void stringListInsertAtIndex(StringLinkedList *list, char const *value, Index const index);

String* stringListPeekBegin(StringLinkedList *list);
String* stringListPeekEnd(StringLinkedList *list);
String* stringListPeekIndex(StringLinkedList *list, Index const index);

void stringListDeleteBegin(StringLinkedList *list);
void stringListDeleteEnd(StringLinkedList *list);
void stringListDeleteIndex(StringLinkedList *list, Index const index);

void stringListLeaveUniques(StringLinkedList *list);
void stringListSort(StringLinkedList *&list);

Index stringListFind(StringLinkedList *list, String *value);
Index stringListFind(StringLinkedList *list, char const *value);
bool stringListIsSorted(StringLinkedList *list);
bool stringListContains(StringLinkedList *list, String *value);
bool stringListContains(StringLinkedList *list, char const *value);
String** stringListGetAsArray(StringLinkedList *list);

void stringListPrint(StringLinkedList *list);
void stringListPrintSiblings(StringLinkedList *list, Index const index);

