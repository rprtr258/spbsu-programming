#pragma once

struct Node;

struct LinkedList {
    Node *head = nullptr;
};

LinkedList* createList();
void listDelete(LinkedList *list);

void listPushBack(LinkedList *list, const int value);
void listPopBack(LinkedList *list);
int listGetFirst(LinkedList *list);
int listGetLast(LinkedList *list);
int listSize(LinkedList *list);

bool listIsEmpty(LinkedList *list);

void listErase(LinkedList *list);

void listPrint(LinkedList *list);

bool listTestModule();
bool listTestCreateErase();
bool listTestPushPop();
bool listTestCheckEmpty();
