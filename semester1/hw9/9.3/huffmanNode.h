#pragma once

struct HuffmanNode {
    HuffmanNode *l = nullptr;
    HuffmanNode *r = nullptr;
    char symbol = '\0';
    int frequency = -1;
};

HuffmanNode* createHuffmanNode(HuffmanNode *leftChild, HuffmanNode *rightChild);
HuffmanNode* createHuffmanNode(char const symbol, int const frequency);

void deleteHuffmanNode(HuffmanNode *node);

HuffmanNode* copy(HuffmanNode *node);

bool isLeaf(HuffmanNode *node);
