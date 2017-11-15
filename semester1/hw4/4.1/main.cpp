#include <stdio.h>
#include <algorithm>

// deprecated, use allocateMemorySmart
int** allocateMemoryStupid(const int sizeI, const int sizeJ) {
    int **result = new int*[sizeI];
    for (int i = 0; i < sizeI; i++)
        result[i] = new int[sizeJ];
    return result;
}

int** allocateMemorySmart(const int sizeI, const int sizeJ) {
    int **result = new int*[sizeI];
    int *temp = new int[sizeI * sizeJ];
    for (int i = 0; i < sizeI; i++)
        result[i] = temp + sizeJ * i;
    return result;
}

void print2D(int **matrix, const int sizeI, const int sizeJ) {
    for (int i = 0; i < sizeI; i++) {
        for (int j = 0; j < sizeJ; j++)
            printf("%d ", matrix[i][j]);
        printf("\n");
    }
}

enum Direction {
    left,
    right,
    up,
    down
};

void travelMatrix(int *result, int **matrix, const int size) {
    int posI = 0;
    int posJ = 0;
    Direction dir = right;
    int ptr = 0;
    
    int leftBound = 0;
    int rightBound = size - 1;
    int upBound = 0;
    int downBound = size - 1;
    
    while (posI != size / 2 || posJ != size / 2) {
        switch (dir) {
            case left: {
                while (posJ - 1 >= leftBound) {
                    result[ptr] = matrix[posI][posJ];
                    ptr++;
                    posJ--;
                }
                downBound--;
                dir = up;
                break;
            }
            case right: {
                while (posJ + 1 <= rightBound) {
                    result[ptr] = matrix[posI][posJ];
                    ptr++;
                    posJ++;
                }
                upBound++;
                dir = down;
                break;
            }
            case up: {
                while (posI - 1 >= upBound) {
                    result[ptr] = matrix[posI][posJ];
                    ptr++;
                    posI--;
                }
                leftBound++;
                dir = right;
                break;
            }
            case down: {
                while (posI + 1 <= downBound) {
                    result[ptr] = matrix[posI][posJ];
                    ptr++;
                    posI++;
                }
                rightBound--;
                dir = left;
                break;
            }
        }
    }
    result[ptr] = matrix[size / 2][size / 2]; // center element
    std::reverse(result, result + size * size);
}

bool testAllocate() {
    const int anySize = 100;
    int **temp = allocateMemorySmart(anySize, 3 * anySize);
    delete[] temp[0];
    delete[] temp;
    
    return true;
}

bool testSize1() {
    int **temp = allocateMemorySmart(1, 1);
    temp[0][0] = 1;
    int *result = new int[1];
    travelMatrix(result, temp, 1);
    if (result[0] != 1)
        return false;
    delete[] temp[0];
    delete[] temp;
    delete[] result;
        
    return true;
}

bool testSize3() {
    int **temp = allocateMemorySmart(3, 3);
    /*
        0 1 2
        3 4 5
        6 7 8
    */
    for (int i = 0; i < 3; i++)
        for (int j = 0; j < 3; j++)
            temp[i][j] = i * 3 + j;
    int result[9];
    travelMatrix(result, temp, 3);
    
    int correct[9] = {4, 3, 6, 7, 8, 5, 2, 1, 0};
    for (int i = 0; i < 9; i++)
        if (result[i] != correct[i])
            return false;
            
    delete[] temp[0];
    delete[] temp;
        
    return true;
}

bool runTests() {
    bool result = true;
    if (!testAllocate()) {
        printf("Allocate test failed\n");
        result = false;
    }
    if (!testSize1()) {
        printf("1x1 matrix test failed\n");
        result = false;
    }
    if (!testSize3()) {
        printf("3x3 matrix test failed\n");
        result = false;
    }
    return result;
}

int main() {
//    if (!runTests())
//        return 0;
    
    printf("Printing square array in spiral order\n");
    printf("Enter array dimension size: ");
    int sizeN = 0;
    while (sizeN % 2 == 0) {
        scanf("%d", &sizeN);
        if (sizeN % 2 == 0)
            printf("Size must be odd\n");
    }
    
    int **matrix = allocateMemorySmart(sizeN, sizeN);
    printf("Write your square matrix, row by row:\n");
    for (int i = 0; i < sizeN; i++)
        for (int j = 0; j < sizeN; j++)
            scanf("%d", &matrix[i][j]);
//    print2D(matrix, sizeN, sizeN);

    int *travel = new int[sizeN * sizeN];
    travelMatrix(travel, matrix, sizeN);
    
    printf("Spiral travel of this matrix:\n");
    for (int i = 0; i < sizeN * sizeN; i++)
        printf("%d ", travel[i]);
    
    delete[] travel;
    delete[] matrix[0];
    delete[] matrix;
    return 0;
}
