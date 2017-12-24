#pragma once
#include "../huffmanNode.h"

struct ListNode;

struct LinkedList {
    ListNode *head = nullptr;
    ListNode *tail = nullptr;
    int size = 0;
};

LinkedList* createList();
void eraseList(LinkedList *list);
void deleteList(LinkedList *&list);

bool isEmpty(LinkedList *list);

void insertAtEnd(LinkedList *list, HuffmanNode *value);
void insertAtBegin(LinkedList *list, HuffmanNode *value);
void insertAtIndex(LinkedList *list, HuffmanNode *value, int const index);

HuffmanNode* popBegin(LinkedList *list);
HuffmanNode* popEnd(LinkedList *list);
HuffmanNode* popIndex(LinkedList *list, int const index);

HuffmanNode* peekBegin(LinkedList *list);
HuffmanNode* peekEnd(LinkedList *list);
HuffmanNode* peekIndex(LinkedList *list, int const index);

void deleteBegin(LinkedList *list);
void deleteEnd(LinkedList *list);
void deleteIndex(LinkedList *list, int const index);
