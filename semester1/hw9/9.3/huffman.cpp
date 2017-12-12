#include <string.h>
#include <stdio.h>
#include <math.h>
#include <utility>
#include <algorithm>
#include <queue>
#include <stack>
#include "huffman.h"
#include "freqtable.h"

int const alphabet = 256;
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

Node* buildTree(char const *str) {
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
    
    FrequencyTable *ftable = createFreqTable(str);
    
    for (int i = 0; i < ftable->size; i++) {
        Node *leaf = new Node();
        leaf->symbol = ftable->data[i].first;
        leaf->frequency = ftable->data[i].second;
        firstQueue.push(leaf);
    }
    
    for (int i = 0; i < ftable->size - 1; i++) {
        Node *first = getRarestNode();
        Node *second = getRarestNode();
        Node *parent = new Node();
        parent->l = first;
        parent->r = second;
        parent->frequency = first->frequency + second->frequency;
        secondQueue.push(parent);
    }
    
    erase(ftable);
    delete ftable;
    return secondQueue.front();
}

HuffmanTree* createTree(const char *str) {
    HuffmanTree *tree = new HuffmanTree();
    tree->root = buildTree(str);
    return tree;
}

HuffmanTree* readTree(const char *filename) {
    char symbol = '\0';
    std::stack<Node*> tempStack;
    FILE *file = fopen(filename, "r");
    while (symbol != '\n') {
        fscanf(file, "%c", &symbol);
        if (symbol == '\n')
            break;
        switch (symbol) {
            case '\0': {
                Node *node = new Node();
                node->symbol = '\n';
                
                tempStack.push(node);
                break;
            }
            case separator: {
                Node *node = new Node();
                
                Node *rightChild = tempStack.top();
                tempStack.pop();
                
                Node *leftChild = tempStack.top();
                tempStack.pop();
                
                node->l = leftChild;
                node->r = rightChild;
                
                tempStack.push(node);
                break;
            }
            default: {
                Node *node = new Node();
                node->symbol = symbol;
                
                tempStack.push(node);
                break;
            }
        }
    }
    
    fclose(file);
    
    HuffmanTree *result = new HuffmanTree();
    result->root = tempStack.top();
    return result;
}

int writeCodes(Node *node, char **codes, char *buffer, int const level = 0) {
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

char* decodeFile(HuffmanTree *tree, const char *fileInput) {
    char symbol = '\0';
    FILE *file = fopen(fileInput, "r");
    
    while (symbol != '\n')
        fscanf(file, "%c", &symbol);
    
    Node *temp = tree->root;
    char *result = new char[1];
    int length = 0;
    result[0] = '\0';
    while (!feof(file)) {
        fscanf(file, "%c", &symbol);
        if (feof(file))
            break;
        while (!isLeaf(temp)) {
            if (symbol == '0')
                temp = temp->l;
            else
                temp = temp->r;
            if (isLeaf(temp))
                break;
            fscanf(file, "%c", &symbol);
        }
        
        char newSymbol = temp->symbol;
        char *newResult = new char[length + 2];
        strcpy(newResult, result);
        newResult[length] = newSymbol;
        newResult[length + 1] = '\0';
        length++;
        delete[] result;
        result = newResult;
        
        temp = tree->root;
    }
    
    fclose(file);
    
    return result;
}

void saveNode(Node *node, FILE *file) {
    if (isLeaf(node)) {
        // WARNING \n encoded as \0 so there is only one line for tree
        fprintf(file, "%c", (node->symbol == '\n' ? '\0' : node->symbol));
    } else {
        saveNode(node->l, file);
        saveNode(node->r, file);
        fprintf(file, "%c", separator);
    }
}

void saveTree(HuffmanTree *tree, FILE *file) {
    saveNode(tree->root, file);
    fprintf(file, "\n");
}

int traverse(Node *node, char *buffer, FILE* file, int const textLength, int &codeSum, double &entropy, int const level = 0) {
    if (isLeaf(node)) {
        buffer[level] = '\0';
        
        double probability = (double)node->frequency / textLength;
        entropy += probability * log(probability) / log(2);
        codeSum += level * node->frequency;
        
        if (node->symbol == '\n')
            fprintf(file, "\'\\n\'");
        else
            fprintf(file, "\'%c\' ", node->symbol);
        fprintf(file, "(ASCII code: %02X): frequency: %3d, code: %s", (int)node->symbol, node->frequency, buffer);
        if (node->symbol == '\n')
            fprintf(file, ", P(\\n) = %.9f\n", probability);
        else
            fprintf(file, ", P(%c) = %.9f\n", node->symbol, probability);
        
        return node->frequency * level;
    }
    
    int codeLength = 0;
    
    buffer[level] = '0';
    codeLength += traverse(node->l, buffer, file, textLength, codeSum, entropy, level + 1);
    
    buffer[level] = '1';
    codeLength += traverse(node->r, buffer, file, textLength, codeSum, entropy, level + 1);
    
    return codeLength;
}

void saveInfo(HuffmanTree *tree, FILE *file, int const textLength) {
    char buffer[1000];
    int codeSum = 0;
    double entropy = 0;
    fprintf(file, "Frequency table:\n");
    int codeLength = traverse(tree->root, buffer, file, textLength, codeSum, entropy);
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
