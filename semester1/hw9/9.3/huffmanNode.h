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
int getHeight(HuffmanNode *node);
int getCodeLength(HuffmanNode *node, int const level = 0);
double getEntropy(HuffmanNode *node, int const textLength);

void saveNodeInfo(HuffmanNode *node, char *buffer, FILE* file, int const textLength, int const level = 0);
