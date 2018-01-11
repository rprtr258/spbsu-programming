#pragma once
#include "../string/string.h"

struct Node;

struct LinkedList {
    Node *head = nullptr;
    Node *tail = nullptr;
    int size = 0;
};

LinkedList* createList();
void eraseList(LinkedList *list);
void deleteList(LinkedList *&list);

void insert(LinkedList *list, String *value, int const count = 1);

bool hasElement(LinkedList *list, String *value);
int findElement(LinkedList *list, String *value);

String* getValueById(LinkedList *list, int const index);
int getCountById(LinkedList *list, int const index);

void printList(LinkedList *list);
