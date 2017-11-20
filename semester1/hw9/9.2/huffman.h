#pragma once
#include <utility>

typedef std::pair<char, int> CharOccur;
struct Node;

struct HuffmanTree {
    Node *root = nullptr;
};

HuffmanTree* createTree(const char *str);
char* encode(HuffmanTree *tree, const char *str);

void saveTree(HuffmanTree *tree, FILE *outFile);
void saveInfo(HuffmanTree *tree, FILE *outFile, int const codeLength, int const textLength);

void debug(HuffmanTree *tree);
CharOccur* makeFreqTable(const char *str, int &resultLength);

void erase(HuffmanTree *tree);