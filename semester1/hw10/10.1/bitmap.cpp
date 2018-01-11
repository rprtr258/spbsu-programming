#include <string.h>
#include <stdio.h>
#include "bitmap.h"
#include "coordinate.h"

void bitMapAddRow(BitMap *map, const char *row) {
    char **newData = new char*[map->height + 1];
    if (map->data != nullptr) {
        memcpy(newData, map->data, map->height * sizeof(char*));
    	delete[] map->data;
    }
    
    newData[map->height] = new char[map->width];
    memcpy(newData[map->height], row, map->width);
    
    map->data = newData;
    map->height++;
}

BitMap* bitMapRead(const char *filename) {
    FILE *file = fopen(filename, "r");
    char tempRow[10001];
    
    BitMap *result = new BitMap();
    while (!feof(file)) {
        fgets(tempRow, 10000, file);
        if (feof(file))
            break;
        int curRowLength = strlen(tempRow) - 1;
        if (result->width != -1 && curRowLength != result->width) {
            printf("Incorrect row length in line %d\n", result->height);
            return nullptr;
        }
        result->width = curRowLength;
        bitMapAddRow(result, tempRow);
    }
    fclose(file);
    
    return result;
}

void bitMapDelete(BitMap *&map) {
    if (map == nullptr)
        return;
    for (int i = 0; i < map->height; i++)
        delete[] map->data[i];
    delete[] map->data;
    delete map;
    map = nullptr;
}

void bitMapPrint(BitMap *map) {
    if (map == nullptr) {
        printf("(Uninitialized map)\n");
        return;
    }
    
    printf("    ");
    for (int i = 0; i < map->width; i++) {
        if (i + 1 >= 100)
            printf("%d", (i + 1) / 100);
        else
            printf(" ");
    }
    printf("\n    ");
    for (int i = 0; i < map->width; i++) {
        if (i + 1 >= 10)
            printf("%d", ((i + 1) / 10) % 10);
        else
            printf(" ");
    }
    printf("\n    ");
    for (int i = 0; i < map->width; i++)
        printf("%d", (i + 1) % 10);
    printf("\n    ");
    for (int i = 0; i < map->width; i++)
        printf("_");
    printf("\n");
    for (int i = 0; i < map->height; i++) {
        printf("%3d|", i + 1);
        for (int j = 0; j < map->width; j++)
            switch (map->data[i][j]) {
                case '0': {
                    printf(".");
                    break;
                }
                case '1': {
                    printf("#");
                    break;
                }
                default: {
                    printf("%c", map->data[i][j]);
                    break;
                }
            }
        printf("\n");
    }
}

bool bitMapIsInside(BitMap *map, Coordinate const *pos) {
    if (pos == nullptr || map == nullptr)
        return false;
    return (0 <= pos->i && pos->i < map->height &&
            0 <= pos->j && pos->j < map->width);
}

