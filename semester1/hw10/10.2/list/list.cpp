#include <stdio.h>
#include "list.h"

struct Node {
    int value = 0;
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
    list->tail = nullptr;
    list->size = 0;
}

void insertAtEnd(LinkedList *list, int const value) {
    if (list == nullptr)
        return;
    
    Node *node = new Node();
    node->value = value;
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

void insertAtBegin(LinkedList *list, int const value) {
    if (list == nullptr)
        return;
    
    Node *node = new Node();
    node->value = value;
    if (list->head == nullptr) {
        list->head = node;
        list->tail = node;
    } else {
        list->head->prev = node;
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
    
    if (index == list->size) {
        insertAtEnd(list, value);
        return;
    }
    
    if (index == 0) {
        insertAtBegin(list, value);
        return;
    }
    
    Node *node = new Node();
    node->value = value;
    
    Node *temp = getListNode(list, index);

    temp->prev->next = node;
    node->prev = temp->prev;
    node->next = temp;
    temp->prev = node;
    
    list->size++;
}

int peekBegin(LinkedList *list) {
    if (list == nullptr || list->size == 0)
        return -1;
    
    return list->head->value;
}

int peekEnd(LinkedList *list) {
    if (list == nullptr || list->size == 0)
        return -1;
    
    return list->tail->value;
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
        list->tail = nullptr;
    } else {
        Node *temp = list->head->next;
        delete list->head;
        list->head = temp;
        temp->prev = nullptr;
    }
    list->size--;
}

void deleteEnd(LinkedList *list) {
    if (list == nullptr || list->size == 0)
        return;
    
    if (list->size == 1) {
        delete list->tail;
        list->head = nullptr;
        list->tail = nullptr;
    } else {
        Node *temp = list->tail->prev;
        delete list->tail;
        list->tail = temp;
        temp->next = nullptr;
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
    
    if (index == list->size - 1) {
        deleteEnd(list);
        return;
    }
    
    Node *temp = getListNode(list, index);
    temp->prev->next = temp->next;
    temp->next->prev = temp->prev;
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

void printList(LinkedList *list) {
    if (list == nullptr)
        return;
    
    Node *temp = list->head;
    while (temp != nullptr) {
        printf("%d", temp->value);
        if (temp->next != nullptr)
            printf(" ");
        temp = temp->next;
    }
}

void printNode(Node *node) {
    if (node == nullptr)
        printf("(null)");
    else
        printf("%d", node->value);
}

void printSiblings(LinkedList *list, int const index) {
    if (list == nullptr)
        return;
    if (index < 0 || index >= list->size)
        return;
    
    Node *temp = getListNode(list, index);
    
    printf("prev: ");
    printNode(temp->prev);
    printf("\nnext: ");
    printNode(temp->next);
    printf("\n");
}
