#include <stdio.h>
#include <string.h>
#include "coordinate.h"
#include "heap.h"
#include "nodeinfo.h"
#include "bitmap.h"
#include "astar.h"

bool doesFileExist(const char *filename) {
    FILE *temp = fopen(filename, "r");
    if (temp == NULL)
        return false;
    fclose(temp);
    return true;
}

bool inBounds(int const value, int const leftBound, int const rightBound) {
    return (leftBound <= value && value <= rightBound);
}

bool askUser(const char *question) {
    printf("%s(y/n)\n", question);
    char temp = '$';
    while (temp != 'y' && temp != 'n') {
        scanf(" %c", &temp);
        if (temp != 'y' && temp != 'n')
            printf("Sorry, please repeat\n");
    }
    return (temp == 'y');
}

Coordinate* readCoordinates(BitMap *map, const char *message) {
    int coordI = -1;
    int coordJ = -1;
    while (true) {
        printf("%s: ", message);
        scanf("%d %d", &coordI, &coordJ);
        if (!inBounds(coordI, 1, map->height))
            printf("First coordinate must be in range 1...%d\n", map->height);
        else if (!inBounds(coordJ, 1, map->width))
            printf("Second coordinate must be in range 1...%d\n", map->width);
        else if (map->data[coordI - 1][coordJ - 1] == '1')
            printf("Position is in wall!\n");
        else
            break;
    }
    return coordCreate(coordI - 1, coordJ - 1);
}

int main() {
    printf("Path finder 2000\n");
    printf("Write your map in \"file.txt\"\n");
    if (!doesFileExist("file.txt")) {
        printf("\"file.txt\" not found\n");
        return 0;
    }
    
    BitMap *map = bitMapRead("file.txt");
    bitMapPrint(map);
    printf("Map size is %dx%d\n", map->height, map->width);
    
    Coordinate *start = readCoordinates(map, "Write coordinates of start");
    Coordinate *dest = readCoordinates(map, "Write coordinates of destination");
    
    bool found = searchAStar(map, start, dest);
    
    if (found) {
        printf("Result(\'#\' is wall, \'.\' is empty cell, \'<\', \'>\', \'^\', \'v\' shows path):\n");
        bitMapPrint(map);
    } else {
        printf("Path doesn't exist\n");
    }
    
    bitMapDelete(map);
    delete start;
    delete dest;
    return 0;
}
