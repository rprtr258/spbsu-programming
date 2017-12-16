#pragma once

struct HuffmanNode {
    HuffmanNode *l = nullptr;
    HuffmanNode *r = nullptr;
    char symbol = '\0';
    int frequency = -1;
};

void deleteHuffmanNode(HuffmanNode *node);

HuffmanNode* copy(HuffmanNode *node);

bool isLeaf(HuffmanNode *node);
