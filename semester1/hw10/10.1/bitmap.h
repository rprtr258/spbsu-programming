#pragma once
#include "coordinate.h"

struct BitMap {
    char **data = nullptr;
    int width = -1;
    int height = 0;
};

BitMap* bitMapRead(const char *filename);

void bitMapDelete(BitMap *&map);

void bitMapPrint(BitMap *map);
bool bitMapIsInside(BitMap *map, Coordinate const *pos);

void bitMapAddRow(BitMap *map, const char *row);
