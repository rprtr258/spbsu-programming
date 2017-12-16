#include "node.h"

bool isLeaf(HuffmanNode *node) {
    return (node->l == nullptr && node->r == nullptr);
}