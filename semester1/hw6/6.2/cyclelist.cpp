#include <stdio.h>
#include "cyclelist.h"

struct ListElement {
    ListElement *next = nullptr;
    ListElement *prev = nullptr;
    int value = 0;
};

CycleList* listCreate(const int size) {
    CycleList *list = new CycleList();
    ListElement *head = new ListElement();
    head->value = 1;
    list->cur = head;
    for (int i = 2; i <= size; i++) {
        ListElement *node = new ListElement();
        node->value = i;

        node->prev = list->cur;
        list->cur->next = node;

        list->cur = node;
    }
    list->cur->next = head;
    head->prev = list->cur;
    
    list->cur = head;
    list->size = size;
    return list;
}

void printList(CycleList *list) {
    ListElement *tmp = list->cur;
    printf("list of size %d: (", list->size);
    for (int i = 0; i < list->size; i++) {
        printf("%d", tmp->value);
        if (i < list->size - 1)
            printf(", ");
        tmp = tmp->next;
    }
    printf(")\n");
}

void listMoveCurrent(CycleList *list, const int steps) {
    for (int i = 0; i < steps; i++) {
        list->cur = list->cur->next;
    }
}

int listGetCurrent(CycleList *list) {
    return list->cur->value;
}

int listSize(CycleList *list) {
    return list->size;
}

void listRemoveCurrent(CycleList *list) {
    ListElement *prev = list->cur->prev;
    ListElement *next = list->cur->next;
    if (list->size > 1) {
        prev->next = next;
        next->prev = prev;
    }
    delete list->cur;
    list->cur = next;
    list->size--;
}

void listClear(CycleList *list) {
    ListElement *tmp = list->cur->next;
    ListElement *end = list->cur->prev;
    while (tmp != end) {
        delete tmp->prev;
        tmp = tmp->next;
    }
    delete tmp->prev;
    if (list->size > 1)
        delete tmp;
    list->cur = nullptr;
    list->size = 0;
}
