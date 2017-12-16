#include <string.h>
#include <stdio.h>
#include <math.h>
#include <stack>
#include "list/list.h"
#include "huffman.h"
#include "huffmanNode.h"
#include "freqtable.h"

int const alphabet = 256;
char const separator = '\7';

HuffmanNode* getRarestNode(LinkedList *firstQueue, LinkedList *secondQueue) {
    HuffmanNode *result = nullptr;
    if (firstQueue->size == 0) {
        result = peekBegin(secondQueue);
        deleteBegin(secondQueue);
    } else if (secondQueue->size == 0) {
        result = peekBegin(firstQueue);
        deleteBegin(firstQueue);
    } else {
        HuffmanNode *tempFirst = peekBegin(firstQueue);
        HuffmanNode *tempSecond = peekBegin(secondQueue);
        if (tempFirst->frequency < tempSecond->frequency) {
            result = peekBegin(firstQueue);
            deleteBegin(firstQueue);
        } else {
            result = peekBegin(secondQueue);
            deleteBegin(secondQueue);
        }
        delete tempFirst;
        delete tempSecond;
    }
    return result;
};

HuffmanNode* buildTree(char const *str) {
    LinkedList *firstQueue = createList();
    LinkedList *secondQueue = createList();
    
    FrequencyTable *ftable = createFreqTable(str);
    
    for (int i = 0; i < ftable->size; i++) {
        HuffmanNode *leaf = new HuffmanNode();
        leaf->symbol = ftable->data[i].first;
        leaf->frequency = ftable->data[i].second;
        insertAtBegin(firstQueue, leaf);
        delete leaf;
    }
    
    for (int i = 0; i < ftable->size - 1; i++) {
        HuffmanNode *first = getRarestNode(firstQueue, secondQueue);
        HuffmanNode *second = getRarestNode(firstQueue, secondQueue);
        HuffmanNode *parent = new HuffmanNode();
        parent->l = first;
        parent->r = second;
        parent->frequency = first->frequency + second->frequency;
        insertAtBegin(secondQueue, parent);
        delete parent;
    }
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

HuffmanTree* readTree(const char *filename) {
    char symbol = '\0';
    std::stack<HuffmanNode*> tempStack;
    FILE *file = fopen(filename, "r");
    while (symbol != '\n') {
        fscanf(file, "%c", &symbol);
        if (symbol == '\n')
            break;
        switch (symbol) {
            case '\0': {
                HuffmanNode *node = new HuffmanNode();
                node->symbol = '\n';
                
                tempStack.push(node);
                break;
            }
            case separator: {
                HuffmanNode *node = new HuffmanNode();
                
                HuffmanNode *rightChild = tempStack.top();
                tempStack.pop();
                
                HuffmanNode *leftChild = tempStack.top();
                tempStack.pop();
                
                node->l = leftChild;
                node->r = rightChild;
                
                tempStack.push(node);
                break;
            }
            default: {
                HuffmanNode *node = new HuffmanNode();
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

char* decodeFile(HuffmanTree *tree, const char *fileInput) {
    char symbol = '\0';
    FILE *file = fopen(fileInput, "r");
    
    while (symbol != '\n')
        fscanf(file, "%c", &symbol);
    
    HuffmanNode *temp = tree->root;
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

int traverse(HuffmanNode *node, char *buffer, FILE* file, int const textLength, int &codeSum, double &entropy, int const level = 0) {
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

void erase(HuffmanNode *node) {
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
