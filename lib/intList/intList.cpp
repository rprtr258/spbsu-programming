#include <stdio.h>
#include "intList.h"

struct Node {
    int value = 0;
    Node *prev = nullptr;
    Node *next = nullptr;
};

Node* getListNode(IntLinkedList *list, int unsigned const index) {
    if (index >= list->size)
        return nullptr;
    
    Node *result = list->head;
    for (int unsigned i = 0; i < index; i++)
        result = result->next;
    return result;
}

IntLinkedList* intListCreate() {
    IntLinkedList *result = new IntLinkedList();
    return result;
}

IntLinkedList* intListCopy(IntLinkedList *other) {
    if (other == nullptr)
        return nullptr;
    
    IntLinkedList *result = new IntLinkedList();
    for (int unsigned i = 0; i < other->size; i++) {
        int value = intListPeekIndex(other, i);
        intListInsertAtEnd(result, value);
    }
    return result;
}

IntLinkedList* intListMergeSorted(IntLinkedList *first, IntLinkedList *second) {
    if (first == nullptr || second == nullptr)
        return nullptr;
    if (!intListIsSorted(first) || !intListIsSorted(second))
        return nullptr;
    
    IntLinkedList *result = intListCreate();
    int unsigned i1 = 0;
    int unsigned i2 = 0;
    while (i1 < first->size && i2 < second->size) {
        int firstValue = intListPeekIndex(first, i1);
        int secondValue = intListPeekIndex(second, i2);
        if (firstValue < secondValue) {
            intListInsertAtEnd(result, firstValue);
            i1++;
        } else {
            intListInsertAtEnd(result, secondValue);
            i2++;
        }
    }
    while (i1 < first->size) {
        int value = intListPeekIndex(first, i1);
        intListInsertAtEnd(result, value);
        i1++;
    }
    while (i2 < second->size) {
        int value = intListPeekIndex(second, i2);
        intListInsertAtEnd(result, value);
        i2++;
    }
    return result;
}

void deleteNodes(IntLinkedList *list) {
    Node *tmp = list->head;
    for (int unsigned i = 0; i < list->size; i++) {
        Node *nextTmp = tmp->next;
        delete tmp;
        tmp = nextTmp;
    }
}

void intListErase(IntLinkedList *list) {
    if (list == nullptr)
        return;
    
    deleteNodes(list);
    list->head = nullptr;
    list->tail = nullptr;
    list->size = 0;
}

void intListDelete(IntLinkedList *&list) {
    if (list == nullptr)
        return;
    
    intListErase(list);
    delete list;
    list = nullptr;
}

void intListInsertAtEnd(IntLinkedList *list, int const value) {
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

void intListInsertAtBegin(IntLinkedList *list, int const value) {
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

void intListInsertAtIndex(IntLinkedList *list, int const value, int unsigned const index) {
    if (list == nullptr)
        return;
    
    if (index > list->size)
        return;
    
    if (index == list->size) {
        intListInsertAtEnd(list, value);
        return;
    }
    
    if (index == 0) {
        intListInsertAtBegin(list, value);
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

int intListPeekBegin(IntLinkedList *list) {
    if (list == nullptr || list->size == 0)
        return -1;
    
    return list->head->value;
}

int intListPeekEnd(IntLinkedList *list) {
    if (list == nullptr || list->size == 0)
        return -1;
    
    return list->tail->value;
}

int intListPeekIndex(IntLinkedList *list, int unsigned const index) {
    if (list == nullptr || list->size == 0)
        return -1;
    
    if (index >= list->size)
        return -1;
    
    Node *temp = getListNode(list, index);
    
    return temp->value;
}

void intListDeleteBegin(IntLinkedList *list) {
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

void intListDeleteEnd(IntLinkedList *list) {
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

void intListDeleteIndex(IntLinkedList *list, int unsigned const index) {
    if (list == nullptr || list->size == 0)
        return;
    
    if (index >= list->size)
        return;
    
    if (index == 0) {
        intListDeleteBegin(list);
        return;
    }
    
    if (index == list->size - 1) {
        intListDeleteEnd(list);
        return;
    }
    
    Node *temp = getListNode(list, index);
    temp->prev->next = temp->next;
    temp->next->prev = temp->prev;
    delete temp;
    
    list->size--;
}

void intListLeaveUniques(IntLinkedList *list) {
    if (list == nullptr)
        return;
    for (int unsigned i = 0; i < list->size; i++) {
        int value = intListPeekIndex(list, i);
        int unsigned prev = intListFind(list, value);
        if (prev != list->size && prev < i) {
            intListDeleteIndex(list, i);
            i--;
        }
    }
}

void mergeSort(IntLinkedList *&list) {
    if (list->size == 1)
        return;
    
    int half = list->size / 2;
    IntLinkedList *first = intListCreate();
    IntLinkedList *second = intListCreate();
    
    for (int i = 0; i < half; i++) {
        int value = intListPeekIndex(list, i);
        intListInsertAtEnd(first, value);
    }
    for (int unsigned i = half; i < list->size; i++) {
        int value = intListPeekIndex(list, i);
        intListInsertAtEnd(second, value);
    }
    
    mergeSort(first);
    mergeSort(second);
    intListDelete(list);
    
    list = intListMergeSorted(first, second);
    
    intListDelete(first);
    intListDelete(second);
}

void intListSort(IntLinkedList *&list) {
    if (list == nullptr || list->size == 0)
        return;
    
    mergeSort(list);
}

int unsigned intListFind(IntLinkedList *list, int const value) {
    if (list == nullptr)
        return 287;
    
    int unsigned result = list->size;
    
    int unsigned i = 0;
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

bool intListIsSorted(IntLinkedList *list) {
    if (list == nullptr)
        return false;
    int result = true;
    for (int unsigned i = 0; i + 1 < list->size; i++) {
        int value = intListPeekIndex(list, i);
        int nextValue = intListPeekIndex(list, i + 1);
        if (value > nextValue) {
            result = false;
            break;
        }
    }
    return result;
}

int* intListGetAsArray(IntLinkedList *list) {
    if (list == nullptr)
        return nullptr;
    
    int *result = new int[list->size];
    int i = 0;
    Node *node = list->head;
    while (node != nullptr) {
        result[i] = node->value;
        node = node->next;
        i++;
    }
    return result;
}

void intListPrint(IntLinkedList *list) {
    if (list == nullptr)
        return;
    
    printf("[");
    Node *temp = list->head;
    while (temp != nullptr) {
        printf("%d", temp->value);
        if (temp->next != nullptr)
            printf(", ");
        temp = temp->next;
    }
    printf("]\n");
}

void printNode(Node *node) {
    if (node == nullptr)
        printf("(null)");
    else
        printf("%d", node->value);
}

void intListPrintSiblings(IntLinkedList *list, int unsigned const index) {
    if (list == nullptr)
        return;
    if (index >= list->size)
        return;
    
    Node *temp = getListNode(list, index);
    
    printf("prev: ");
    printNode(temp->prev);
    printf("\nnext: ");
    printNode(temp->next);
    printf("\n");
}

