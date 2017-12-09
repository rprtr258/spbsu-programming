#include <stdio.h>
#include "list.h"

struct Node {
    int value = 0;
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

void deleteNode(Node *node) {
    if (node == nullptr)
        return;
    
    if (node->next != nullptr)
        deleteNode(node->next);
    delete node;
}

void deleteList(LinkedList *&list) {
    if (list == nullptr)
        return;
    
    deleteNode(list->head);
    list = nullptr;
}

void eraseList(LinkedList *list) {
    if (list == nullptr)
        return;
    
    deleteNode(list->head);
    list->head = nullptr;
    list->size = 0;
}

void insertAtBegin(LinkedList *list, int const value) {
    if (list == nullptr)
        return;
    
    Node *node = new Node();
    node->value = value;
    if (list->head == nullptr) {
        list->head = node;
    } else {
        node->next = list->head;
        list->head = node;
    }
    list->size++;
}

void insertAtIndex(LinkedList *list, int const value, int const index) {
    if (list == nullptr)
        return;
    
    if (index < 0 || index > list->size)
        return;
    
    if (index == 0) {
        insertAtBegin(list, value);
        return;
    }
    
    Node *node = new Node();
    node->value = value;
    
    if (index == list->size) {
        Node *temp = getListNode(list, list->size - 1);
        temp->next = node;
        list->size++;
        return;
    }
    
    Node *temp = getListNode(list, index);

    node->next = temp;
    
    list->size++;
}

int peekBegin(LinkedList *list) {
    if (list == nullptr || list->size == 0)
        return -1;
    
    return list->head->value;
}

int peekIndex(LinkedList *list, int const index) {
    if (list == nullptr || list->size == 0)
        return -1;
    
    if (index < 0 || index >= list->size)
        return -1;
    
    Node *temp = getListNode(list, index);
    
    return temp->value;
}

void deleteBegin(LinkedList *list) {
    if (list == nullptr || list->size == 0)
        return;
    
    if (list->size == 1) {
        delete list->head;
        list->head = nullptr;
    } else {
        Node *temp = list->head->next;
        delete list->head;
        list->head = temp;
    }
    list->size--;
}

void deleteIndex(LinkedList *list, int const index) {
    if (list == nullptr || list->size == 0)
        return;
    
    if (index < 0 || index >= list->size)
        return;
    
    if (index == 0) {
        deleteBegin(list);
        return;
    }
    
    Node *temp = getListNode(list, index);
    delete temp;
    
    list->size--;
}

int findElement(LinkedList *list, int const value) {
    if (list == nullptr)
        return -1;
    
    int result = -1;
    
    int i = 0;
    Node *temp = list->head;
    while (temp != nullptr) {
        if (temp->value == value) {
            result = i;
            break;
        }
        temp = temp->next;
        i++;
    }
    
    return result;
}

int lowerBound(LinkedList *list, int const value) {
    if (list == nullptr)
        return -1;
    
    int result = -1;
    
    int i = 0;
    Node *temp = list->head;
    while (temp != nullptr) {
        if (temp->value <= value)
            result = i;

        temp = temp->next;
        i++;
    }
    
    return result + 1;
}

void printNode(Node *node) {
    if (node == nullptr)
        printf("(null)");
    else
        printf("%d", node->value);
}

void printList(LinkedList *list) {
    if (list == nullptr)
        return;
    
    Node *temp = list->head;
    while (temp != nullptr) {
        printNode(temp);
        if (temp->next != nullptr)
            printf(" ");
        temp = temp->next;
    }
}

void printSiblings(LinkedList *list, int const index) {
    if (list == nullptr)
        return;
    if (index < 0 || index >= list->size)
        return;
    
    Node *temp = getListNode(list, index);
    
    printf("\nnext: ");
    printNode(temp->next);
    printf("\n");
}
