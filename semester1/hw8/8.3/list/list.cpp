#include <stdio.h>
#include "../string/string.h"
#include "list.h"

struct Node {
    String *value = nullptr;
    int count = 0;
    Node *prev = nullptr;
    Node *next = nullptr;
};

Node* getListNode(LinkedList *list, int const index) {
    if (index < 0 || index >= list->size)
        return nullptr;
    
    Node *result = list->head;
    for (int i = 0; i < index; i++)
        result = result->next;
    return result;
}

LinkedList* createList() {
    LinkedList *result = new LinkedList();
    return result;
}

void deleteSingleNode(Node *&node) {
    if (node == nullptr)
        return;
    
    deleteString(node->value);
    delete node;
    node = nullptr;
}

void deleteNode(Node *node) {
    if (node == nullptr)
        return;
    
    if (node->next != nullptr)
        deleteNode(node->next);
    
    deleteSingleNode(node);
}

void deleteList(LinkedList *&list) {
    if (list == nullptr)
        return;
    
    deleteNode(list->head);
    delete list;
    list = nullptr;
}

void eraseList(LinkedList *list) {
    if (list == nullptr)
        return;
    
    deleteNode(list->head);
    list->head = nullptr;
    list->tail = nullptr;
    list->size = 0;
}

void insert(LinkedList *list, String *value, int const count) {
    if (list == nullptr)
        return;
    
    if (hasElement(list, value)) {
        int index = findElement(list, value);
        Node *temp = getListNode(list, index);
        temp->count += count;
        return;
    }
    
    Node *node = new Node();
    node->value = cloneString(value);
    node->count = count;
    
    if (list->head == nullptr) {
        list->head = node;
        list->tail = node;
    } else {
        list->tail->next = node;
        node->prev = list->tail;
        list->tail = node;
    }
    list->size++;
}

bool hasElement(LinkedList *list, String *value) {
    return (findElement(list, value) != -1);
}

int findElement(LinkedList *list, String *value) {
    if (list == nullptr)
        return -1;
    
    int result = -1;
    int i = 0;
    Node *temp = list->head;
    while (temp != nullptr) {
        if (areEqual(temp->value, value)) {
            result = i;
            break;
        }
        temp = temp->next;
        i++;
    }
    
    return result;
}

String* getValueById(LinkedList *list, int const index) {
    Node *temp = getListNode(list, index);
    return cloneString(temp->value);
}

int getCountById(LinkedList *list, int const index) {
    Node *temp = getListNode(list, index);
    return temp->count;
}

void printNode(Node *node) {
    if (node == nullptr)
        printf("(null)");
    else {
        printString(node->value);
        printf("(%d)", node->count);
    }
}

void printList(LinkedList *list) {
    if (list == nullptr)
        return;
    
    printf("[");
    Node *temp = list->head;
    while (temp != nullptr) {
        printNode(temp);
        if (temp->next != nullptr)
            printf(", ");
        temp = temp->next;
    }
    printf("]\n");
}
