#include <string.h>
#include <stdio.h>
#include "bitmap.h"

void bitMapDelete(BitMap *&map) {
    if (map == nullptr)
        return;
    for (int i = 0; i < map->height; i++)
        delete[] map->data[i];
    delete map;
    map = nullptr;
}

void bitMapPrint(BitMap *map) {
    
    printf("    ");
    for (int i = 0; i < map->width; i++) {
        if (i + 1 >= 100)
            printf("%d", (i + 1) / 100);
        else
            printf(" ");
    }
    printf("\n");
    printf("    ");
    for (int i = 0; i < map->width; i++) {
        if (i + 1 >= 10)
            printf("%d", ((i + 1) / 10) % 10);
        else
            printf(" ");
    }
    printf("\n");
    printf("    ");
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

bool bitMapIsInside(BitMap *map, int const i, int const j) {
    return (0 <= i && i < map->height &&
            0 <= j && j < map->width);
}

void bitMapAddRow(BitMap *map, const char *row) {
    char **newData = new char*[map->height + 1];
    if (map->data != nullptr)
        memcpy(newData, map->data, map->height * sizeof(char*));
    delete[] map->data;
    
    newData[map->height] = new char[map->width];
    memcpy(newData[map->height], row, map->width);
    
    map->data = newData;
    map->height++;
}
