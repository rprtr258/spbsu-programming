#pragma once

struct NodeInfo {
    int dist = -1;
    int h = -1; // heuristic
    int i = -1, j = -1; // coordinates
};

NodeInfo* nodeInfoCreate(int dist, int h, int i, int j);
NodeInfo* nodeInfoCopy(NodeInfo *node);

int nodeInfoGetEstimation(NodeInfo *node);
int nodeInfoGetHeuristic(NodeInfo *node, int const destI, int const destJ);
