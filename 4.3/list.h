#pragma once

struct LinkedList;

LinkedList* createList();

void listPushBack(LinkedList *list, const char value);
void listPopBack(LinkedList *list);
char listGetLast(LinkedList *list);

bool listIsEmpty(LinkedList *list);

void listErase(LinkedList *list);
