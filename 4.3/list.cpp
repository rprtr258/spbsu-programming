#include "list.h"

struct Node {
    char val = '\0';
    Node *next = nullptr;
};

struct LinkedList {
    Node *head = nullptr;
};

LinkedList* createList() {
    return new LinkedList;
}

Node* beforeLastNode(LinkedList *list) {
    Node *temp = list->head;
    while (temp->next->next != nullptr)
        temp = temp->next;
    return temp;
}

Node* lastNode(LinkedList *list) {
    Node *temp = list->head;
    while (temp->next != nullptr)
        temp = temp->next;
    return temp;
}

void listPushBack(LinkedList *list, const char value) {
    Node *newNode = new Node;
    newNode->val = value;
    if (list->head == nullptr) {
        list->head = newNode;
    } else {
        Node *last = lastNode(list);
        last->next = newNode;
    }
}

void listPopBack(LinkedList *list) {
    if (list->head->next == nullptr) {
        delete list->head;
        list->head = nullptr;
    } else {
        Node *temp = beforeLastNode(list);    
        delete temp->next;
        temp->next = nullptr;
    }
}

char listGetLast(LinkedList *list) {
    if (list->head->next == nullptr) {
        return list->head->val;
    } else {
        Node *temp = beforeLastNode(list);    
        return temp->next->val;
    }
}

bool listIsEmpty(LinkedList *list) {
    return list->head == nullptr;
}

void listErase(LinkedList *list) {
    while (list->head != nullptr)
        listPopBack(list);
}
