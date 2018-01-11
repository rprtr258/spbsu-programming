#pragma once

struct ListElement;

struct CycleList {
    ListElement *cur = nullptr;
    int size = 0;
};

CycleList* listCreate(const int size, int const lowestValue, int const highestValue);
void listDelete(CycleList *&list);

void listPrint(CycleList *list);

void listReverse(CycleList *list);

int listSize(CycleList *list);
