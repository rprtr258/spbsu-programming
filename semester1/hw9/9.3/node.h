#pragma once

struct Node {
    Node *l = nullptr;
    Node *r = nullptr;
    char symbol = '\0';
    int frequency = -1;
};

bool isLeaf(Node *node);