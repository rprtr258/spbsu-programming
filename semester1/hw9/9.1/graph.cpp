#include <stdio.h>
#include <limits.h>
#include <algorithm>
#include "graph.h"

struct Edge {
    int from = -1;
    int to = -1;
    int weight = 0;
};

Graph* parseFile(const char *filename) {
    Graph *graph = new Graph();
    
    FILE *file = fopen(filename, "r");
    fscanf(file, "%d %d", &graph->vertexes, &graph->edges);
    graph->edgesList = new Edge[graph->edges];
    for (int i = 0; i < graph->edges; i++) {
        Edge &curEdge = graph->edgesList[i];
        fscanf(file, "%d %d %d", &curEdge.from, &curEdge.to, &curEdge.weight);
        // to 0-index from 1-index
        curEdge.from--;
        curEdge.to--;
    }
    
    fclose(file);
    return graph;
}

void dijkstra(Graph *graph) {
    bool *marked = new bool[graph->vertexes];
    for (int i = 0; i < graph->vertexes; i++)
        marked[i] = false;
    
    graph->minpath = new int[graph->vertexes];
    graph->minpath[0] = 0;
    for (int i = 1; i < graph->vertexes; i++)
        graph->minpath[i] = INT_MAX;
    
    graph->from = new int[graph->vertexes];
    graph->from[0] = 0;
    for (int i = 1; i < graph->vertexes; i++)
        graph->from[i] = -1;
    
    while (true) {
        int v = -1;
        for (int i = 0; i < graph->vertexes; i++)
            if (!marked[i] && (v == -1 || graph->minpath[i] < graph->minpath[v]))
                v = i;
            
        if (v == -1 || graph->minpath[v] == INT_MAX)
            break;
        
        marked[v] = true;
        for (int i = 0; i < graph->edges; i++) {
            Edge &curEdge = graph->edgesList[i];
            if (curEdge.from != v && curEdge.to != v)
                continue;
            int to = (curEdge.from == v ? curEdge.to : curEdge.from);
            if (graph->minpath[to] > graph->minpath[v] + curEdge.weight) {
                graph->minpath[to] = graph->minpath[v] + curEdge.weight;
                graph->from[to] = v;
            }
        }
    }
    delete[] marked;
}

void printPath(Graph *graph, int const city, bool last = true) {
    if (city != 0)
        printPath(graph, graph->from[city], false);
    
    printf("%d", city + 1);
    if (!last)
        printf(" -> ");
}

void printInfo(Graph *graph) {
    int *cities = new int[graph->vertexes];
    for (int i = 0; i < graph->vertexes; i++)
        cities[i] = i;
    std::sort(cities, cities + graph->vertexes, [&](int const city1, int const city2) {
        return graph->minpath[city1] < graph->minpath[city2];
    });
    printf("Cities visited in visit order:\n\n");
    for (int i = 0; i < graph->vertexes && graph->minpath[i] != INT_MAX; i++) {
        printf("#%d: City %d, distance %d\n", i + 1, cities[i] + 1, graph->minpath[cities[i]]);
        printf("Path: ");
        printPath(graph, cities[i]);
        printf("\n\n");
    }
    
    delete[] cities;
}

void erase(Graph *graph) {
    delete[] graph->edgesList;
    graph->edgesList = nullptr;
    
    if (graph->from != nullptr) {
        delete[] graph->from;
        graph->from = nullptr;
    }
    
    if (graph->minpath != nullptr) {
        delete[] graph->minpath;
        graph->minpath = nullptr;
    }
    
    graph->vertexes = 0;
    graph->edges = 0;
}
