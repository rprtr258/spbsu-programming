#include <stdio.h>
#include <stdlib.h>
#include "cyclelist.h"

struct ListElement {
    ListElement *next = nullptr;
    int value = 0;
};

int generateValue(int const lowestValue, int const highestValue) {
    return rand() % (highestValue - lowestValue + 1) + lowestValue;
}

CycleList* listCreate(int const size, int const lowestValue, int const highestValue) {
    CycleList *list = new CycleList();
    ListElement *head = new ListElement();
    head->value = generateValue(lowestValue, highestValue);
    list->cur = head;
    for (int i = 0; i < size - 1; i++) {
        ListElement *node = new ListElement();
        node->value = generateValue(lowestValue, highestValue);

        list->cur->next = node;

        list->cur = node;
    }
    list->cur->next = head;
    list->cur = head;
    list->size = size;
    return list;
}

void listPrint(CycleList *list) {
    if (list == nullptr) {
        printf("Empty list\n");
        return;
    }
    
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

void listReverse(CycleList *list) {
    if (list == nullptr)
        return;
    
    ListElement **nodes = new ListElement*[list->size];
    ListElement *temp = list->cur;
    for (int i = 0; i < list->size; i++) {
        nodes[i] = temp;
        temp = temp->next;
    }
    for (int i = 1; i < list->size; i++)
        nodes[i]->next = nodes[i - 1];
    nodes[0]->next = nodes[list->size - 1];
    list->cur = list->cur->next;
    delete[] nodes;
}

int listSize(CycleList *list) {
    return list->size;
}

void listDelete(CycleList *&list) {
    if (list == nullptr)
        return;
    
    ListElement *tmp = list->cur->next;
    while (tmp->next != list->cur) {
        ListElement *next = tmp->next;
        delete tmp;
        tmp = next;
    }
    delete tmp;
    delete list;
    list = nullptr;
}
