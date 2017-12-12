#pragma once
#include <stdio.h>
#include <utility>

struct Node;

struct HuffmanTree {
    Node *root = nullptr;
};

HuffmanTree* createTree(const char *str);
HuffmanTree* readTree(const char *filename);

char* encode(HuffmanTree *tree, const char *str);
char* decodeFile(HuffmanTree *tree, const char *fileInput);

void saveTree(HuffmanTree *tree, FILE *file);
void saveInfo(HuffmanTree *tree, FILE *file, int const textLength);

void debug(HuffmanTree *tree);

void erase(HuffmanTree *tree);