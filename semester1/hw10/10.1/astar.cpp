#include <limits.h>
#include <windows.h>
#include "astar.h"
#include "heap.h"

struct Pair {
    int i = -1;
    int j = -1;
};

void relax(NodeInfo *vertex, BitMap *map, int **dist, Pair ***from, Heap *heap, int const destI, int const destJ, int const di, int const dj, char const arrow) {
    int vertI = vertex->i;
    int vertJ = vertex->j;
    if (bitMapIsInside(map, vertI + di, vertJ + dj) && map->data[vertI + di][vertJ + dj] != '1') {
        if (dist[vertI + di][vertJ + dj] > vertex->dist + 1) {
            dist[vertI + di][vertJ + dj] = vertex->dist + 1;
            
            from[vertI + di][vertJ + dj]->i = vertI;
            from[vertI + di][vertJ + dj]->j = vertJ;
            
            map->data[vertI + di][vertJ + dj] = arrow;
            
            NodeInfo *neighbour = nodeInfoCreate(vertex->dist + 1, -1, vertI + di, vertJ + dj);
            int heuristic = nodeInfoGetHeuristic(neighbour, destI, destJ);
            neighbour->h = heuristic;
            heapPush(heap, neighbour);
            
            delete neighbour;
        }
    }
}

bool searchAStar(BitMap *map, int const startI, int const startJ, int const destI, int const destJ, bool printProccess) {
    Pair ***from = new Pair**[map->height];
    for (int i = 0; i < map->height; i++) {
        from[i] = new Pair*[map->width];
        for (int j = 0; j < map->width; j++)
            from[i][j] = new Pair();
    }
    
    int **dist = new int*[map->height];
    for (int i = 0; i < map->height; i++) {
        dist[i] = new int[map->width];
        for (int j = 0; j < map->width; j++)
            dist[i][j] = INT_MAX;
    }
    
    Heap *heap = heapCreate(nodeInfoGetEstimation);
    NodeInfo *start = nodeInfoCreate(0, 0, startI, startJ);
    dist[startI][startJ] = 0;
    
    heapPush(heap, start);
    while (heap->size > 0) {
        NodeInfo *vertex = heapPop(heap);
        
        if (dist[vertex->i][vertex->j] < vertex->dist)
            continue;
        
        if (vertex->i == destI && vertex->j == destJ) {
            break;
            delete vertex;
        }
        
        relax(vertex, map, dist, from, heap, destI, destJ, 0, -1, '<');
        relax(vertex, map, dist, from, heap, destI, destJ, 0, 1, '>');
        relax(vertex, map, dist, from, heap, destI, destJ, 1, 0, 'v');
        relax(vertex, map, dist, from, heap, destI, destJ, -1, 0, '^');

        if (printProccess) {
            system("cls");
            bitMapPrint(map);
        }
        delete vertex;
    }
    
    bool result = (map->data[destI][destJ] != '0');
    
    // clear arrows
    for (int i = 0; i < map->height; i++) {
        for (int j = 0; j < map->width; j++)
            if (map->data[i][j] != '0' && map->data[i][j] != '1')
                map->data[i][j] = '0';
    }
    
    // reconstruct path
    if (result || startI == destI && startJ == destJ) {
        map->data[startI][startJ] = 'O';
        int posI = destI;
        int posJ = destJ;
        while (posI != startI || posJ != startJ) {
            if (from[posI][posJ]->i == posI - 1) {
                map->data[posI][posJ] = 'v';
                posI--;
            }
            if (from[posI][posJ]->i == posI + 1) {
                map->data[posI][posJ] = '^';
                posI++;
            }
            if (from[posI][posJ]->j == posJ - 1) {
                map->data[posI][posJ] = '>';
                posJ--;
            }
            if (from[posI][posJ]->j == posJ + 1) {
                map->data[posI][posJ] = '<';
                posJ++;
            }
        }
    }
    
    for (int i = 0; i < map->height; i++) {
        for (int j = 0; j < map->width; j++)
            delete from[i][j];
        delete[] from[i];
    }
    delete[] from;
    for (int i = 0; i < map->height; i++)
        delete[] dist[i];
    delete[] dist;
    delete start;
    heapDelete(heap);
    
    return result;
}
