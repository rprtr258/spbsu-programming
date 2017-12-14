#include <stdio.h>
#include <string.h>
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

void eraseMemory(BitMap *&map, char *&tempRow) {
    bitMapDelete(map);
    map = nullptr;
    delete[] tempRow;
    tempRow = nullptr;
}

int main() {
    printf("Path finder 2000\n");
    printf("Write your map in \"file.txt\"\n");
    if (!doesFileExist("file.txt")) {
        printf("\"file.txt\" not found\n");
        return 0;
    }
    
    FILE *file = fopen("file.txt", "r");
    BitMap *map = new BitMap();
    char *tempRow = new char[10001];
    while (!feof(file)) {
        fgets(tempRow, 1000, file);
        if (feof(file))
            break;
        int curRowLength = strlen(tempRow) - 1;
        if (map->width != -1 && curRowLength != map->width) {
            printf("Incorrect row length in line %d\n", map->height);
            return 0;
        }
        map->width = curRowLength;
        bitMapAddRow(map, tempRow);
    }
    fclose(file);
    bitMapPrint(map);
    printf("Map size is %dx%d\n", map->height, map->width);
    
    bool areCordsOK = true;
    
    printf("Write coordinates of start: ");
    int startI = 0, startJ = 0;
    scanf("%d %d", &startI, &startJ);
    if (!inBounds(startI, 1, map->height)) {
        printf("First coordinate must be in range 1...%d\n", map->height);
        areCordsOK = false;
    } else if (!inBounds(startJ, 1, map->width)) {
        printf("Second coordinate must be in range 1...%d\n", map->width);
        areCordsOK = false;
    } else if (map->data[startI - 1][startJ - 1] == '1') {
        printf("Start is in wall!\n");
        areCordsOK = false;
    }
    
    if (!areCordsOK) {
        eraseMemory(map, tempRow);
        return 0;
    }
    
    printf("Write coordinates of destination: ");
    int destI = 0, destJ = 0;
    scanf("%d %d", &destI, &destJ);
    if (!inBounds(destI, 1, map->height)) {
        printf("First coordinate must be in range 1...%d\n", map->height);
        areCordsOK = false;
    } else if (!inBounds(destJ, 1, map->width)) {
        printf("Second coordinate must be in range 1...%d\n", map->width);
        areCordsOK = false;
    } else if (map->data[destI - 1][destJ - 1] == '1') {
        printf("Destination is in wall!\n");
        areCordsOK = false;
    } else if (startI == destI && startJ == destJ) {
        printf("Coordinates are the same!\n");
        areCordsOK = false;
    }
    
    if (!areCordsOK) {
        eraseMemory(map, tempRow);
        return 0;
    }
    
    printf("Print proccess of A*(y/n)?(Make sure you don't have epilepsy(i don't know how to do it correctly))\n");
    char temp = '$';
    while (temp != 'y' && temp != 'n') {
        scanf(" %c", &temp);
        if (temp != 'y' && temp != 'n')
            printf("Sorry, please repeat\n");
    }
    bool printProccess = (temp == 'y');
    
    searchAStar(map, startI - 1, startJ - 1, destI - 1, destJ - 1, printProccess);
    
    printf("Result(\'#\' is wall, \'.\' is empty cell, \'<\', \'>\', \'^\', \'v\' shows path):\n");
    bitMapPrint(map);
    
    eraseMemory(map, tempRow);
    return 0;
}
