#include <stdio.h>
#include "graph.h"

bool doesFileExist(const char *filename) {
    FILE *file = fopen(filename, "r");
    if (file == NULL)
        return false;
    fclose(file);
    return true;
}

int main() {
    printf("War analyzer program\n");
    printf("Make sure you wrote correct info in \"info.txt\"\n\n");
    if (!doesFileExist("info.txt")) {
        printf("\"info.txt\" not found\n");
        return 0;
    }
    
    Graph *graph = parseFile("info.txt");
    dijkstra(graph);
    printInfo(graph);
    
    erase(graph);
    delete graph;    
    return 0;
}