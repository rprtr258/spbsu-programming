#include "huffmanNode.h"

HuffmanNode* copy(HuffmanNode *node) {
    if (node == nullptr)
        return nullptr;
    
    HuffmanNode *result = new HuffmanNode();
    result->l = node->l;
    result->r = node->r;
    result->symbol = node->symbol;
    result->frequency = node->frequency;
    
    return result;
}

bool isLeaf(HuffmanNode *node) {
    return (node->l == nullptr && node->r == nullptr);
}