#include "dsu.h"

DSU* createDSU(int const size) {
    DSU *newDSU = new DSU();
    newDSU->data = new int[size];
    for (int i = 0; i < size; i++)
        newDSU->data[i] = i;
    newDSU->size = size;
    
    return newDSU;
}

int parent(DSU *dsu, int const x) {
    if (dsu->data[x] == x)
        return x;
    else {
        int result = parent(dsu, dsu->data[x]);
        dsu->data[x] = result;
        return result;
    }
}

void merge(DSU *dsu, int const x, int const y) {
    if (areInOneSet(dsu, x, y))
        return;
    int xParent = parent(dsu, x);
    int yParent = parent(dsu, y);
    dsu->data[xParent] = yParent;
}

bool areInOneSet(DSU *dsu, int const x, int const y) {
    return (getSetIndex(dsu, x) == getSetIndex(dsu, y));
}

int getSetIndex(DSU *dsu, int const x) {
    return parent(dsu, x);
}

void erase(DSU *dsu) {
    delete[] dsu->data;
}
