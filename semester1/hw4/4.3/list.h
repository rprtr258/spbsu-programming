#pragma once

struct Node;

struct LinkedList {
    Node *head = nullptr;
};

LinkedList* createList();

void listPushBack(LinkedList *list, const char value);
void listPopBack(LinkedList *list);
char listGetLast(LinkedList *list);

bool listIsEmpty(LinkedList *list);

void listErase(LinkedList *list);

bool listTestModule();
bool listTestCreateErase();
bool listTestPushPop();
bool listTestCheckEmpty();
