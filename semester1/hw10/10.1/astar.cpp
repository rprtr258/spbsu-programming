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

void relaxNeighbours(NodeInfo *vertex, BitMap *map, int **dist, Coordinate ***from, Heap *heap, Coordinate const *dest) {
    int const directions = 4;
    int const move[directions][2] = {
        {-1, 0},
        {1, 0},
        {0, -1},
        {0, 1}
    };
    
    for (int i = 0; i < directions; i++) {
        Coordinate *newPos = add(vertex->coord, move[i][0], move[i][1]);
        if (bitMapIsInside(map, newPos) && getElement(map->data, newPos) != '1') {
            if (getElement(dist, newPos) > vertex->dist + 1) {
                setElement(dist, newPos, vertex->dist + 1);

                coordDelete(from[newPos->i][newPos->j]);
                from[newPos->i][newPos->j] = coordCopy(vertex->coord);

                NodeInfo *neighbour = nodeInfoCreate(vertex->dist + 1, coordDist(newPos, dest), newPos);
                heapPush(heap, neighbour);

                nodeInfoDelete(neighbour);
            }
        }
        coordDelete(newPos);
    }
}

void reconstructPath(BitMap *map, Coordinate const *start, Coordinate const *dest, Coordinate ***from) {
    setElement(map->data, start, 'O');
    Coordinate *curPos = coordCopy(dest);
    while (!coordEquals(curPos, start)) {
        if (from[curPos->i][curPos->j]->i == curPos->i - 1) {
            setElement(map->data, curPos, 'v');
            curPos->i--;
        } else if (from[curPos->i][curPos->j]->i == curPos->i + 1) {
            setElement(map->data, curPos, '^');
            curPos->i++;
        } else if (from[curPos->i][curPos->j]->j == curPos->j - 1) {
            setElement(map->data, curPos, '>');
            curPos->j--;
        } else if (from[curPos->i][curPos->j]->j == curPos->j + 1) {
            setElement(map->data, curPos, '<');
            curPos->j++;
        }
    }
    coordDelete(curPos);
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
        
        if (getElement(dist, vertex->coord) < vertex->dist) {
            nodeInfoDelete(vertex);
            continue;
        }
        if (coordEquals(vertex->coord, dest)) {
            nodeInfoDelete(vertex);
            break;
        }
        
        relaxNeighbours(vertex, map, dist, from, heap, dest);

        nodeInfoDelete(vertex);
    }
    
    bool result = (getElement(dist, dest) != INT_MAX);
    result |= coordEquals(start, dest);
    
    // reconstruct path
    if (result)
        reconstructPath(map, start, dest, from);
    
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
