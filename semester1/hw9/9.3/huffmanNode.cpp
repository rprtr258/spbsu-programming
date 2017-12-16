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
