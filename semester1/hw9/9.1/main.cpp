#include <stdio.h>
#include "graph.h"

int main() {
    printf("War analyzer program\n");
    printf("Make sure you wrote correct info in \"info.txt\"\n\n");
    
    Graph *graph = parseFile("info.txt");
    dijkstra(graph);
    printInfo(graph);
    
    erase(graph);
    delete graph;    
    return 0;
}