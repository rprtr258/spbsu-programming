#pragma once

struct ListElement;

struct CycleList {
    ListElement *cur = nullptr;
    int size = 0;
};

CycleList* listCreate(const int size);

void printList(CycleList *list);

void listMoveCurrent(CycleList *list, const int steps);
int listGetCurrent(CycleList *list);
void listRemoveCurrent(CycleList *list);

int listSize(CycleList *list);
void listClear(CycleList *list);
