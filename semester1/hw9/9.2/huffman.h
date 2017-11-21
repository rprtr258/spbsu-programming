#pragma once
#include <stdio.h>
#include <utility>

struct Node;

struct HuffmanTree {
    Node *root = nullptr;
};

HuffmanTree* createTree(const char *str);
char* encode(HuffmanTree *tree, const char *str);

void saveTree(HuffmanTree *tree, FILE *file);
void saveInfo(HuffmanTree *tree, FILE *file, int const textLength);

void debug(HuffmanTree *tree);

void erase(HuffmanTree *tree);