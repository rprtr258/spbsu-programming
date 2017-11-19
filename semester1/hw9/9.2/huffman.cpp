#include <string.h>
#include <stdio.h>
#include <math.h>
#include <utility>
#include <algorithm>
#include <queue>
#include "huffman.h"

int const alphabet = 128;
char const separator = '\7';

struct Node {
    Node *l = nullptr;
    Node *r = nullptr;
    char symbol = '\0';
    int frequency = -1;
};

bool isLeaf(Node *node) {
    return (node->l == nullptr && node->r == nullptr);
}

Node* buildTree(CharOccur *table, int const tableSize) {
    std::queue<Node*> firstQueue;
    std::queue<Node*> secondQueue;
    auto getRarestNode = [&]() {
        Node *result = nullptr;
        if (firstQueue.empty()) {
            result = secondQueue.front();
            secondQueue.pop();
        } else if (secondQueue.empty()) {
            result = firstQueue.front();
            firstQueue.pop();
        } else if (secondQueue.front()->frequency < firstQueue.front()->frequency) {
            result = secondQueue.front();
            secondQueue.pop();
        } else {
            result = firstQueue.front();
            firstQueue.pop();
        }
        return result;
    };
    
    for (int i = 0; i < tableSize; i++) {
        Node *leaf = new Node();
        leaf->symbol = table[i].first;
        leaf->frequency = table[i].second;
        firstQueue.push(leaf);
    }
    
    for (int i = 0; i < tableSize - 1; i++) {
        Node *first = getRarestNode();
        Node *second = getRarestNode();
        Node *parent = new Node();
        parent->l = first;
        parent->r = second;
        parent->frequency = first->frequency + second->frequency;
        secondQueue.push(parent);
    }
    
    return getRarestNode();
}

HuffmanTree* createTree(const char *str) {
    HuffmanTree *tree = new HuffmanTree();
    
    int tableSize = -1;
    CharOccur *table = makeFreqTable(str, tableSize);
    
    tree->root = buildTree(table, tableSize);
    
    delete[] table;
    return tree;
}

int writeCodes(Node *node, char **codes, char *buffer, FILE* file = stdin, int const level = 0) {
    if (isLeaf(node)) {
        codes[(int)node->symbol] = new char[level + 1];
        buffer[level] = '\0';
        strcpy(codes[(int)node->symbol], buffer);
        
        if (node->symbol == '\n')
            fprintf(file, "\'\\n\'(ASCII code: %02X): frequency: %3d, code: %s\n", (int)node->symbol, node->frequency, buffer);
        else
            fprintf(file, "\'%c\' (ASCII code: %02X): frequency: %3d, code: %s\n", node->symbol, (int)node->symbol, node->frequency, buffer);
        
        return node->frequency * level;
    }
    int result = 0;
    buffer[level] = '0';
    result += writeCodes(node->l, codes, buffer, file, level + 1);
    buffer[level] = '1';
    result += writeCodes(node->r, codes, buffer, file, level + 1);
    return result;
}

char* encode(HuffmanTree *tree, const char *str) {
    int strLength = strlen(str);
    
    char **codes = new char*[alphabet];
    for (int i = 0; i < alphabet; i++)
        codes[i] = nullptr;
    
    char buffer[1000];
    int resultLength = writeCodes(tree->root, codes, buffer);
    
    char *result = new char[resultLength + 1];
    int j = 0;
    for (int i = 0; i < strLength; i++) {
        int codeLength = strlen(codes[(int)str[i]]);
        for (int k = 0; k < codeLength; k++)
            result[j + k] = codes[(int)str[i]][k];
        j += codeLength;
    }
    result[resultLength] = '\0';
    
    return result;
}

CharOccur* makeFreqTable(const char *str, int &resultLength) {
    int charCount[alphabet];
    for (int i = 0; i < alphabet; i++)
        charCount[i] = 0;
    
    int strLength = strlen(str);
    for (int i = 0; i < strLength; i++)
        charCount[(int)str[i]]++;
    
    CharOccur table[alphabet];
    for (int i = 0; i < alphabet; i++)
        table[i] = CharOccur((char)i, charCount[i]);
    std::sort(table, table + alphabet, [](const CharOccur &occur1, const CharOccur &occur2) {
        return occur1.second < occur2.second;
    });
    
    int ptr = 0;
    while (table[ptr].second == 0)
        ptr++;
    
    CharOccur *freqTable = new CharOccur[alphabet - ptr];
    for (int i = ptr; i < alphabet; i++)
        freqTable[i - ptr] = table[i];
    
    // for (int i = 0; i < alphabet - ptr; i++)
        // printf("%c: %d\n", freqTable[i].first, freqTable[i].second);
    
    resultLength = alphabet - ptr;
    return freqTable;
}

void debug(Node *node, FILE *file = stdin) {
    if (node == nullptr)
        return;
    
    if (isLeaf(node)) {
        // WARNING \n encoded as \0 so there is one line for tree
        fprintf(file, "%c", (node->symbol == '\n' ? '\0' : node->symbol));
    } else {
        debug(node->l, file);
        debug(node->r, file);
        fprintf(file, "%c", separator);
    }
}

void debug(HuffmanTree *tree, FILE *file = stdin) {
    debug(tree->root, file);
    fprintf(file, "\n");
}

void saveTree(HuffmanTree *tree, FILE *outFile = stdin) {
    debug(tree, outFile);
}

void traverse(Node *node, char *buffer, FILE* file, int const textLength, int &codeSum, double &entropy, int const level = 0) {
    if (isLeaf(node)) {
        buffer[level] = '\0';
        
        double probability = (double)node->frequency / textLength;
        entropy += probability * log(probability) / log(2);
        codeSum += level * node->frequency;
        
        if (node->symbol == '\n')
            fprintf(file, "\'\\n\'(ASCII code: %02X): frequency: %3d, code: %s, P(\\n) = %.9f\n", (int)node->symbol, node->frequency, buffer, probability);
        else
            fprintf(file, "\'%c\' (ASCII code: %02X): frequency: %3d, code: %s, P(%c) = %.9f\n", node->symbol, (int)node->symbol, node->frequency, buffer, node->symbol, probability);
        
        return;
    }
    buffer[level] = '0';
    traverse(node->l, buffer, file, textLength, codeSum, entropy, level + 1);
    buffer[level] = '1';
    traverse(node->r, buffer, file, textLength, codeSum, entropy, level + 1);
}

void saveInfo(HuffmanTree *tree, FILE *file, int const codeLength, int const textLength) {
    char buffer[1000];
    int codeSum = 0;
    double entropy = 0;
    fprintf(file, "Frequency table:\n");
    traverse(tree->root, buffer, file, textLength, codeSum, entropy);
    entropy *= -1;
    
    fprintf(file, "Entropy: %.20f\n", entropy);
    fprintf(file, "Expected code length: %.20f\n", (double)codeSum / textLength);
    fprintf(file, "Length of text: %d\n", textLength * 8);
    fprintf(file, "Length of encoded text: %d\n", codeLength);
    fprintf(file, "Compression coeff.: %.20f\n", (8.0 * textLength) / codeLength);
}

void erase(Node *node) {
    if (node == nullptr)
        return;
    erase(node->l);
    erase(node->r);
    delete node;
}

void erase(HuffmanTree *tree) {
    erase(tree->root);
    tree->root = nullptr;
}