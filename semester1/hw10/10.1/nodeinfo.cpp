#include "nodeinfo.h"
#include "coordinate.h"

NodeInfo* nodeInfoCreate(int dist, int h, Coordinate const *coord) {
    NodeInfo *result = new NodeInfo();
    result->dist = dist;
    result->h = h;
    result->coord = coordCopy(coord);
    return result;
}

NodeInfo* nodeInfoCopy(NodeInfo *node) {
    if (node == nullptr)
        return nullptr;
    NodeInfo *result = new NodeInfo();
    result->dist = node->dist;
    result->h = node->h;
    result->coord = node->coord;
    return result;
}

void nodeInfoDelete(NodeInfo *&node) {
    if (node == nullptr)
        return;
    coordDelete(node->coord);
    delete node;
    node = nullptr;
}

int nodeInfoGetEstimation(NodeInfo *node) {
    return node->dist + node->h;
}
