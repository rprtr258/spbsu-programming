#include <stdio.h>
#include <math.h>
#include "huffmanNode.h"

HuffmanNode* createHuffmanNode(HuffmanNode *leftChild, HuffmanNode *rightChild) {
    HuffmanNode *result = new HuffmanNode();
    result->l = copy(leftChild);
    result->r = copy(rightChild);
    return result;
}

HuffmanNode* createHuffmanNode(char const symbol, int const frequency) {
    HuffmanNode *result = new HuffmanNode();
    result->symbol = symbol;
    result->frequency = frequency;
    return result;
}

void deleteHuffmanNode(HuffmanNode *node) {
    if (node == nullptr)
        return;
    deleteHuffmanNode(node->l);
    deleteHuffmanNode(node->r);
    delete node;
}

HuffmanNode* copy(HuffmanNode *node) {
    if (node == nullptr)
        return nullptr;
    
    HuffmanNode *result = new HuffmanNode();
    result->l = copy(node->l);
    result->r = copy(node->r);
    result->symbol = node->symbol;
    result->frequency = node->frequency;
    
    return result;
}

bool isLeaf(HuffmanNode *node) {
    return (node->l == nullptr && node->r == nullptr);
}

int getHeight(HuffmanNode *node) {
    if (isLeaf(node))
        return 0;
    int heightLeft = getHeight(node->l);
    int heightRight = getHeight(node->r);
    return (heightLeft >= heightLeft ? heightLeft : heightRight) + 1;
}

int getCodeLength(HuffmanNode *node, int const level) {
    if (node == nullptr)
        return 0;
    
    if (isLeaf(node))
        return node->frequency * level;
    
    return getCodeLength(node->l, level + 1) + getCodeLength(node->r, level + 1);
}

double getEntropy(HuffmanNode *node, int const textLength) {
    if (node == nullptr)
        return -1;
    
    if (isLeaf(node)) {
        double probability = (double)node->frequency / textLength;
        return probability * log(1.0 / probability) / log(2);
    }
    
    return getEntropy(node->l, textLength) + getEntropy(node->r, textLength);
}

void printEncodedChar(FILE *file, char const symbol) {
    if (symbol == '\n')
        fprintf(file, "\\n");
    else
        fprintf(file, "%c", symbol);
}

void printNodeInfo(HuffmanNode *node, char *buffer, FILE* file, int const textLength) {
    double probability = (double)node->frequency / textLength;

    fprintf(file, "\'");
    printEncodedChar(file, node->symbol);
    fprintf(file, "\'");

    fprintf(file, "(ASCII code: %02X): frequency: %3d, code: %s", (int)node->symbol, node->frequency, buffer);

    fprintf(file, ", P(");
    printEncodedChar(file, node->symbol);
    fprintf(file, ") = %.9f\n", probability);
}

void saveNodeInfo(HuffmanNode *node, char *buffer, FILE* file, int const textLength, int const level) {
    if (isLeaf(node)) {
        buffer[level] = '\0';
        printNodeInfo(node, buffer, file, textLength);
        return;
    }
    
    buffer[level] = '0';
    saveNodeInfo(node->l, buffer, file, textLength, level + 1);
    
    buffer[level] = '1';
    saveNodeInfo(node->r, buffer, file, textLength, level + 1);
}
