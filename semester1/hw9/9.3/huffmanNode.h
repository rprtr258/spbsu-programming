#pragma once

struct HuffmanNode {
    HuffmanNode *l = nullptr;
    HuffmanNode *r = nullptr;
    char symbol = '\0';
    int frequency = -1;
};

bool isLeaf(HuffmanNode *node);