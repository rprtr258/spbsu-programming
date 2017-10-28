#include <stdio.h>
#include "list.h"

struct Node {
    int val = 0;
    Node *next = nullptr;
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

void listPushBack(LinkedList *list, const int value) {
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

int listGetLast(LinkedList *list) {
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

bool listTestModule() {
    bool result = true;
    if (!listTestCreateErase()) {
        printf("List module: Create\\Erase test failed\n");
        result = false;
    }
    if (!listTestPushPop()) {
        printf("List module: Push\\Pop test failed\n");
        result = false;
    }
    if (!listTestCheckEmpty()) {
        printf("List module: IsEmpty test failed\n");
        result = false;
    }
    return result;
}

bool listTestCreateErase() {
    LinkedList *temp = createList();
    listErase(temp);
    return true;
}

bool listTestPushPop() {
    LinkedList *temp = createList();
    for (int i = 0; i < 10; i++)
        listPushBack(temp, i);
    for (int i = 9; i >= 0; i--) {
        if (listGetLast(temp) != i) {
            return false;
        }
        listPopBack(temp);
    }
    listErase(temp);
    return true;
}

bool listTestCheckEmpty() {
    LinkedList *temp = createList();
    if (!listIsEmpty(temp))
        return false;
    listPushBack(temp, 300);
    if (listIsEmpty(temp))
        return false;
    listPopBack(temp);
    if (!listIsEmpty(temp))
        return false;
    listErase(temp);
    return true;
}
