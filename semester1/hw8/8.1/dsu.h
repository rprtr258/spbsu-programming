#pragma once

struct DSU {
    int *data = nullptr;
    int size = 0;
};

DSU* createDSU(int const size);

void merge(DSU *dsu, int const x, int const y);
bool areInOneSet(DSU *dsu, int const x, int const y);
int getSetIndex(DSU *dsu, int const x);

void erase(DSU *dsu);