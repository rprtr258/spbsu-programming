#include "node.h"

bool isLeaf(Node *node) {
    return (node->l == nullptr && node->r == nullptr);
}