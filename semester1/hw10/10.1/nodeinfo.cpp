#include <stdlib.h>
#include "nodeinfo.h"

NodeInfo* nodeInfoCreate(int dist, int h, int i, int j) {
    NodeInfo *result = new NodeInfo();
    result->dist = dist;
    result->h = h;
    result->i = i;
    result->j = j;
    return result;
}

NodeInfo* nodeInfoCopy(NodeInfo *node) {
    if (node == nullptr)
        return nullptr;
    NodeInfo *result = new NodeInfo();
    result->dist = node->dist;
    result->h = node->h;
    result->i = node->i;
    result->j = node->j;
    return result;
}

int nodeInfoGetEstimation(NodeInfo *node) {
    return node->dist + node->h;
}

int nodeInfoGetHeuristic(NodeInfo *node, int const destI, int const destJ) {
    return abs(node->i - destI) + abs(node->j - destJ);
}
