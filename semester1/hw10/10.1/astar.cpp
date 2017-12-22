#include <limits.h>
#include <string.h>
#include "astar.h"
#include "heap.h"
#include "bitmap.h"
#include "coordinate.h"
#include "nodeinfo.h"

char& getElement(char **array, Coordinate const *index) {
    return array[index->i][index->j];
}

int& getElement(int **array, Coordinate const *index) {
    return array[index->i][index->j];
}

void setElement(char **array, Coordinate const *index, char const value) {
    array[index->i][index->j] = value;
}

void setElement(Coordinate ***array, Coordinate const *index, Coordinate *value) {
    array[index->i][index->j] = value;
}

void setElement(int **array, Coordinate const *index, int const value) {
    array[index->i][index->j] = value;
}

void relax(NodeInfo *vertex, BitMap *map, int **dist, Coordinate ***from, Heap *heap, Coordinate const *dest, int const di, int const dj, char const arrow) {
    int vertI = vertex->coord->i;
    int vertJ = vertex->coord->j;
    Coordinate *newPos = coordCreate(vertI + di, vertJ + dj);
    if (bitMapIsInside(map, newPos) && map->data[vertI + di][vertJ + dj] != '1') {
        if (getElement(dist, newPos) > vertex->dist + 1) {
            setElement(dist, newPos, vertex->dist + 1);
            
            coordDelete(from[newPos->i][newPos->j]);
            from[newPos->i][newPos->j] = coordCopy(vertex->coord);
            
            setElement(map->data, newPos, arrow);
            
            NodeInfo *neighbour = nodeInfoCreate(vertex->dist + 1, coordDist(newPos, dest), newPos);
            heapPush(heap, neighbour);
            
            nodeInfoDelete(neighbour);
        }
    }
    coordDelete(newPos);
}

bool searchAStar(BitMap *map, Coordinate const *start, Coordinate const *dest) {
    Coordinate ***from = new Coordinate**[map->height];
    for (int i = 0; i < map->height; i++) {
        from[i] = new Coordinate*[map->width];
        for (int j = 0; j < map->width; j++)
            from[i][j] = nullptr;
    }
    
    int **dist = new int*[map->height];
    for (int i = 0; i < map->height; i++) {
        dist[i] = new int[map->width];
        for (int j = 0; j < map->width; j++)
            dist[i][j] = INT_MAX;
    }
    
    Heap *heap = heapCreate();
    NodeInfo *startNode = nodeInfoCreate(0, 0, start);
    dist[start->i][start->j] = 0;
    
    heapPush(heap, startNode);
    while (heap->size > 0) {
        NodeInfo *vertex = heapPop(heap);
        
        if (dist[vertex->coord->i][vertex->coord->j] < vertex->dist) {
            nodeInfoDelete(vertex);
            continue;
        }
        
        if (coordEquals(vertex->coord, dest)) {
            nodeInfoDelete(vertex);
            break;
        }
        
        relax(vertex, map, dist, from, heap, dest, 0, -1, '<');
        relax(vertex, map, dist, from, heap, dest, 0, 1, '>');
        relax(vertex, map, dist, from, heap, dest, 1, 0, 'v');
        relax(vertex, map, dist, from, heap, dest, -1, 0, '^');

        nodeInfoDelete(vertex);
    }
    
    bool result = (map->data[dest->i][dest->j] != '0');
    
    // clear arrows
    for (int i = 0; i < map->height; i++) {
        for (int j = 0; j < map->width; j++)
            if (map->data[i][j] != '0' && map->data[i][j] != '1')
                map->data[i][j] = '0';
    }
    
    // reconstruct path
    if (result || coordEquals(start, dest)) {
        map->data[start->i][start->j] = 'O';
        int posI = dest->i;
        int posJ = dest->j;
        while (posI != start->i || posJ != start->j) {
            if (from[posI][posJ]->i == posI - 1) {
                map->data[posI][posJ] = 'v';
                posI--;
            } else if (from[posI][posJ]->i == posI + 1) {
                map->data[posI][posJ] = '^';
                posI++;
            } else if (from[posI][posJ]->j == posJ - 1) {
                map->data[posI][posJ] = '>';
                posJ--;
            } else if (from[posI][posJ]->j == posJ + 1) {
                map->data[posI][posJ] = '<';
                posJ++;
            }
        }
    }
    
    for (int i = 0; i < map->height; i++) {
        for (int j = 0; j < map->width; j++)
            coordDelete(from[i][j]);
        delete[] from[i];
    }
    delete[] from;
    for (int i = 0; i < map->height; i++)
        delete[] dist[i];
    delete[] dist;
    nodeInfoDelete(startNode);
    heapDelete(heap);
    
    return result;
}
