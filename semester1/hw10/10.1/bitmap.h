#pragma once

struct BitMap {
    char **data = nullptr;
    int width = -1;
    int height = 0;
};

void bitMapDelete(BitMap *&map);

void bitMapPrint(BitMap *map);
bool bitMapIsInside(BitMap *map, int const i, int const j);

void bitMapAddRow(BitMap *map, const char *row);
