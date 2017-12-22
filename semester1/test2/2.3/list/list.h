#pragma once

struct Node;

struct LinkedList {
    Node *head = nullptr;
    int size = 0;
};

LinkedList* createList();
void eraseList(LinkedList *list);
void deleteList(LinkedList *&list);

void insertAtBegin(LinkedList *list, int const value);
void insertAtIndex(LinkedList *list, int const value, int const index);

int peekBegin(LinkedList *list);
int peekIndex(LinkedList *list, int const index);

void deleteBegin(LinkedList *list);
void deleteIndex(LinkedList *list, int const index);

int findElement(LinkedList *list, int const value);
int lowerBound(LinkedList *list, int const value);

void printList(LinkedList *list);
void printSiblings(LinkedList *list, int const index);
