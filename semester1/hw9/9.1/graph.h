#pragma once

struct Edge;

struct Graph {
    Edge *edgesList = nullptr;
    int *from = nullptr;
    int *minpath = nullptr;
    int vertexes = 0;
    int edges = 0;
};

Graph* parseFile(const char *filename);

void dijkstra(Graph *graph);
void printInfo(Graph *graph);

void erase(Graph *graph);