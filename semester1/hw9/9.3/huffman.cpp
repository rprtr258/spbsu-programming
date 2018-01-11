#include <string.h>
#include <stdio.h>
#include "list/list.h"
#include "huffman.h"
#include "huffmanNode.h"
#include "freqtable.h"
#include "putChar.h"

int const alphabet = 256;
char const separator = '\7';

HuffmanNode* getRarestNode(LinkedList *firstQueue, LinkedList *secondQueue) {
    HuffmanNode *result = nullptr;
    if (firstQueue->size == 0) {
        result = popBegin(secondQueue);
    } else if (secondQueue->size == 0) {
        result = popBegin(firstQueue);
    } else {
        HuffmanNode *tempFirst = peekBegin(firstQueue);
        HuffmanNode *tempSecond = peekBegin(secondQueue);
        
        if (tempFirst->frequency < tempSecond->frequency)
            result = popBegin(firstQueue);
        else
            result = popBegin(secondQueue);
        
        deleteHuffmanNode(tempFirst);
        deleteHuffmanNode(tempSecond);
    }
    return result;
};

void proccessRarest(LinkedList *firstQueue, LinkedList *secondQueue) {
    HuffmanNode *first = getRarestNode(firstQueue, secondQueue);
    HuffmanNode *second = getRarestNode(firstQueue, secondQueue);
    
    HuffmanNode *parent = createHuffmanNode(first, second);
    insertAtEnd(secondQueue, parent);
    
    deleteHuffmanNode(parent);
    deleteHuffmanNode(first);
    deleteHuffmanNode(second);
}

HuffmanNode* buildTree(char const *str) {
    LinkedList *firstQueue = createList();
    LinkedList *secondQueue = createList();
    
    FrequencyTable *ftable = createFreqTable(str);
    
    for (int i = 0; i < ftable->size; i++) {
        HuffmanNode *leaf = createHuffmanNode(ftable->data[i].first, ftable->data[i].second);
        insertAtEnd(firstQueue, leaf);
        deleteHuffmanNode(leaf);
    }
    
    for (int i = 0; i < ftable->size - 1; i++)
        proccessRarest(firstQueue, secondQueue);
    
    HuffmanNode *result = peekBegin(secondQueue);
    
    deleteFreqTable(ftable);
    deleteList(firstQueue);
    deleteList(secondQueue);
    return result;
}

HuffmanTree* createTree(const char *str) {
    HuffmanTree *tree = new HuffmanTree();
    tree->root = buildTree(str);
    return tree;
}

void proccesSymbol(LinkedList *stack, char const symbol) {
    HuffmanNode *node = nullptr;
    if (symbol == separator) {
        HuffmanNode *rightChild = popBegin(stack);
        HuffmanNode *leftChild = popBegin(stack);

        node = createHuffmanNode(leftChild, rightChild);

        deleteHuffmanNode(leftChild);
        deleteHuffmanNode(rightChild);
    } else {
        node = createHuffmanNode(symbol == '\0' ? '\n' : symbol);
    }
    insertAtBegin(stack, node);
    deleteHuffmanNode(node);
}

HuffmanTree* readTree(const char *filename) {
    LinkedList *tempStack = createList();
    FILE *file = fopen(filename, "r");
    
    char symbol = '\0';
    fscanf(file, "%c", &symbol);
    while (symbol != '\n') {
        proccesSymbol(tempStack, symbol);
        fscanf(file, "%c", &symbol);
    }
    
    HuffmanTree *result = new HuffmanTree();
    result->root = peekBegin(tempStack);
    
    deleteList(tempStack);
    fclose(file);
    return result;
}

void deleteTree(HuffmanTree *&tree) {
    if (tree == nullptr)
        return;
    deleteHuffmanNode(tree->root);
    delete tree;
    tree = nullptr;
}

void writeCodes(HuffmanNode *node, char **codes, char *buffer, int const level = 0) {
    if (isLeaf(node)) {
        int unsigned const symbolCode = (int)node->symbol;
        codes[symbolCode] = new char[level + 1];
        memcpy(codes[symbolCode], buffer, level * sizeof(char));
        codes[symbolCode][level] = '\0';
        return;
    }
    buffer[level] = '0';
    writeCodes(node->l, codes, buffer, level + 1);
    buffer[level] = '1';
    writeCodes(node->r, codes, buffer, level + 1);
}

char* encode(HuffmanTree *tree, const char *str) {
    char *codes[alphabet];
    for (int i = 0; i < alphabet; i++)
        codes[i] = nullptr;
    
    char buffer[alphabet];
    writeCodes(tree->root, codes, buffer);
    
    int strLength = strlen(str);
    int resultLength = getCodeLength(tree->root);
    char *result = new char[resultLength + 1];
    int j = 0;
    for (int i = 0; i < strLength; i++) {
        int unsigned const symbolCode = (int)str[i];
        int codeLength = strlen(codes[symbolCode]);
        memcpy(result + j, codes[symbolCode], codeLength * sizeof(char));
        j += codeLength;
    }
    result[resultLength] = '\0';
    
    for (int i = 0; i < alphabet; i++)
        if (codes[i] != nullptr)
            delete[] codes[i];
    return result;
}

char* decodeFile(HuffmanTree *tree, const char *fileInput) {
    char symbol = '\0';
    FILE *file = fopen(fileInput, "r");
    
    while (symbol != '\n')
        fscanf(file, "%c", &symbol);
    
    char *result = new char[1];
    result[0] = '\0';
    fscanf(file, "%c", &symbol);
    while (!feof(file)) {
        char newSymbol = decodeChar(tree->root, file, symbol);
        putChar(result, newSymbol);
        
        fscanf(file, "%c", &symbol);
    }
    
    fclose(file);
    return result;
}

void saveHuffmanNode(HuffmanNode *node, FILE *file) {
    if (isLeaf(node)) {
        // WARNING: \n encoded as \0 so there is only one line for tree
        fprintf(file, "%c", (node->symbol == '\n' ? '\0' : node->symbol));
    } else {
        saveHuffmanNode(node->l, file);
        saveHuffmanNode(node->r, file);
        fprintf(file, "%c", separator);
    }
}

void saveTree(HuffmanTree *tree, FILE *file) {
    saveHuffmanNode(tree->root, file);
    fprintf(file, "\n");
}

void saveInfo(HuffmanTree *tree, FILE *file, int const textLength) {
    char *buffer = new char[alphabet];
    double entropy = getEntropy(tree->root, textLength);
    fprintf(file, "Frequency table:\n");
    int codeLength = getCodeLength(tree->root);
    saveNodeInfo(tree->root, buffer, file, textLength);
    
    fprintf(file, "Entropy: %.20f\n", entropy);
    fprintf(file, "Expected code length: %.20f\n", (double)codeLength / textLength);
    fprintf(file, "Length of text(in bytes): %d\n", textLength);
    fprintf(file, "Length of encoded text(in bytes): %d\n", codeLength / 8);
    fprintf(file, "Compression coeff.: %.20f\n", textLength / (codeLength / 8.0));
    
    delete[] buffer;
}
