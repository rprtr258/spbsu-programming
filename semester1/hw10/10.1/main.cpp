#include <stdio.h>
#include <string.h>

struct BitMap {
    char **data = nullptr;
    int width = -1;
    int height = 0;
};

void deleteMap(BitMap *&map) {
    if (map == nullptr)
        return;
    for (int i = 0; i < map->height; i++)
        delete[] map->data[i];
    delete map;
    map = nullptr;
}

void addRow(BitMap *map, const char *row) {
    char **newData = new char*[map->height + 1];
    if (map->data != nullptr)
        memcpy(newData, map->data, map->height * sizeof(char*));
    delete[] map->data;
    
    newData[map->height] = new char[map->width];
    memcpy(newData[map->height], row, map->width);
    
    map->data = newData;
    map->height++;
}

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
    deleteMap(map);
    map = nullptr;
    delete[] tempRow;
    tempRow = nullptr;
}

void doAStar(BitMap *map, int const startI, int const startJ, int const destI, int const destJ) {
    // TODO: try to display map in proccess
    
    // create FROM array
    // create heap
    // push start in heap
    // cycle till heap isn't empty
        // pop vertex
        // break if it is destination
        // update it's neighbours
        // push them into heap if they were relaxed
    // try to reconstruct path from FROM array and write it on map as arrows <> ^v
}

int main() {
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
        printf("%s", tempRow);
        int curRowLength = strlen(tempRow) - 1;
        if (map->width != -1 && curRowLength != map->width) {
            printf("Incorrect row length in line %d\n", map->height);
            return 0;
        }
        map->width = curRowLength;
        addRow(map, tempRow);
    }
    fclose(file);
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
    
    doAStar(map, startI, startJ, destI, destJ);
    
    eraseMemory(map, tempRow);
    return 0;
}
