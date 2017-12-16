#include <string.h>
#include <stdio.h>
#include "list/list.h"
#include "huffman.h"
#include "huffmanNode.h"
#include "freqtable.h"

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
    parent->frequency = first->frequency + second->frequency;
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
    
    erase(ftable);
    delete ftable;
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
    HuffmanNode *node = new HuffmanNode();
    switch (symbol) {
        case separator: {
            HuffmanNode *rightChild = popBegin(stack);
            HuffmanNode *leftChild = popBegin(stack);
            
            node->l = copy(leftChild);
            node->r = copy(rightChild);
            
            deleteHuffmanNode(leftChild);
            deleteHuffmanNode(rightChild);
            break;
        }
        default: {
            node->symbol = (symbol == '\0' ? '\n' : symbol);
            break;
        }
    }
    insertAtBegin(stack, node);
    delete node;
}

HuffmanTree* readTree(const char *filename) {
    char symbol = '\0';
    LinkedList *tempStack = createList();
    FILE *file = fopen(filename, "r");
    while (symbol != '\n') {
        fscanf(file, "%c", &symbol);
        if (symbol == '\n')
            break;
        
        proccesSymbol(tempStack, symbol);
    }
    
    fclose(file);
    
    HuffmanTree *result = new HuffmanTree();
    result->root = peekBegin(tempStack);
    
    deleteList(tempStack);
    
    return result;
}

int writeCodes(HuffmanNode *node, char **codes, char *buffer, int const level = 0) {
    if (isLeaf(node)) {
        codes[(int)node->symbol] = new char[level + 1];
        memcpy(codes[(int)node->symbol], buffer, level);
        codes[(int)node->symbol][level] = '\0';
        
        return node->frequency * level;
    }
    int result = 0;
    buffer[level] = '0';
    result += writeCodes(node->l, codes, buffer, level + 1);
    buffer[level] = '1';
    result += writeCodes(node->r, codes, buffer, level + 1);
    return result;
}

char* encode(HuffmanTree *tree, const char *str) {
    char *codes[alphabet];
    for (int i = 0; i < alphabet; i++)
        codes[i] = nullptr;
    
    char buffer[1000];
    int resultLength = writeCodes(tree->root, codes, buffer);
    
    int strLength = strlen(str);
    char *result = new char[resultLength + 1];
    int j = 0;
    for (int i = 0; i < strLength; i++) {
        int codeLength = strlen(codes[(int)str[i]]);
        memcpy(result + j, codes[(int)str[i]], codeLength);
        j += codeLength;
    }
    result[resultLength] = '\0';
    
    for (int i = 0; i < alphabet; i++)
        delete[] codes[i];
    return result;
}

char decodeChar(HuffmanTree *tree, FILE *file, char const firstBit) {
    HuffmanNode *temp = tree->root;
    char bit = firstBit;
    while (!isLeaf(temp)) {
        if (bit == '0')
            temp = temp->l;
        else
            temp = temp->r;
        if (isLeaf(temp))
            break;
        fscanf(file, "%c", &bit);
    }
    return temp->symbol;
}

void putChar(char *&string, int &length, char const symbol) {
    char *newString = new char[length + 2];
    strncpy(newString, string, sizeof(char) * (length + 2));
    newString[length] = symbol;
    newString[length + 1] = '\0';
    length++;
    delete[] string;
    string = newString;
}

char* decodeFile(HuffmanTree *tree, const char *fileInput) {
    char symbol = '\0';
    FILE *file = fopen(fileInput, "r");
    
    while (symbol != '\n')
        fscanf(file, "%c", &symbol);
    
    char *result = new char[1];
    int length = 0;
    result[0] = '\0';
    while (!feof(file)) {
        fscanf(file, "%c", &symbol);
        if (feof(file))
            break;
        
        char newSymbol = decodeChar(tree, file, symbol);
        putChar(result, length, newSymbol);
    }
    
    fclose(file);
    
    return result;
}

void saveHuffmanNode(HuffmanNode *node, FILE *file) {
    if (isLeaf(node)) {
        // WARNING \n encoded as \0 so there is only one line for tree
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
    char *buffer = new char[getHeight(tree->root)];
    double entropy = getEntropy(tree->root, textLength);
    fprintf(file, "Frequency table:\n");
    int codeLength = getCodeLength(tree->root);
    saveNodeInfo(tree->root, buffer, file, textLength);
    
    fprintf(file, "Entropy: %.20f\n", entropy);
    fprintf(file, "Expected code length: %.20f\n", (double)codeLength / textLength);
    fprintf(file, "Length of text: %d\n", textLength * 8);
    fprintf(file, "Length of encoded text: %d\n", codeLength);
    fprintf(file, "Compression coeff.: %.20f\n", (8.0 * textLength) / codeLength);
    
    delete[] buffer;
}

void erase(HuffmanTree *tree) {
    deleteHuffmanNode(tree->root);
    tree->root = nullptr;
}
