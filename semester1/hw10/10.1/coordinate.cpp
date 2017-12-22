#include <stdlib.h>
#include "coordinate.h"

Coordinate* coordCreate(int const i, int const j) {
    Coordinate *result = new Coordinate();
    result->i = i;
    result->j = j;
    return result;
}

Coordinate* coordCopy(Coordinate const *other) {
    if (other == nullptr)
        return nullptr;
    
    return coordCreate(other->i, other->j);
}

void coordDelete(Coordinate *&coordinate) {
    if (coordinate == nullptr)
        return;
    delete coordinate;
    coordinate = nullptr;
}

int coordDist(Coordinate const *first, Coordinate const *second) {
    if (first == nullptr || second == nullptr)
        return -1;
    
    return abs(first->i - second->i) + abs(first->j - second->j);
}

bool coordEquals(Coordinate const *first, Coordinate const *second) {
    if (first == nullptr || second == nullptr)
        return false;
    
    return (first->i == second->i && first->j == second->j);
}
