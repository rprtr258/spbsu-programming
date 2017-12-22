#pragma once

struct Coordinate {
    int i = -1;
    int j = -1;
};

Coordinate* coordCreate(int const i, int const j);
Coordinate* coordCopy(Coordinate const *other);
void coordDelete(Coordinate *&coordinate);

int coordDist(Coordinate const *first, Coordinate const *second);
bool coordEquals(Coordinate const *first, Coordinate const *second);
