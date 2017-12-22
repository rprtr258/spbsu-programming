#pragma once
#include "coordinate.h"

struct NodeInfo {
    int dist = -1;
    int h = -1; // heuristic
    Coordinate *coord = nullptr;
};

NodeInfo* nodeInfoCreate(int dist, int h, Coordinate const *coord);
NodeInfo* nodeInfoCopy(NodeInfo *node);
void nodeInfoDelete(NodeInfo *&node);

int nodeInfoGetEstimation(NodeInfo *node);
