#pragma once

struct Node;

struct Set {
    Node *root = nullptr;
    int unsigned size = 0;
};

Set* setCreate();
void setErase(Set *tree);
void setDelete(Set *&tree);

void setInsert(Set *tree, int const value);
void setRemove(Set *tree, int const value);
bool setContains(Set *tree, int const value);

void setPrintIncreasing(Set *tree);
void setPrintDecreasing(Set *tree);
void setPrintDebug(Set *tree);

