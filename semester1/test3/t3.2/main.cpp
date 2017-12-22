#include <stdio.h>
#include <limits.h>
#include <string.h>

int findMinInRow(int **data, int const height, int const width, int const row) {
    int result = INT_MAX;
    for (int i = 0; i < width; i++)
        if (data[row][i] < result)
            result = data[row][i];
    return result;
}

int findMaxInColumn(int **data, int const height, int const width, int const column) {
    int result = INT_MIN;
    for (int i = 0; i < height; i++)
        if (data[i][column] > result)
            result = data[i][column];
    return result;
}

int main() {
    printf("Saddle points searcher\n");
    
    int width = 0, height = 0;
    printf("Write height and width of array: ");
    scanf("%d %d", &height, &width);
    while (height <= 0 || width <= 0) {
        printf("Incorrect array size\n");
        printf("Write height and width of array: ");
        scanf("%d %d", &height, &width);
    }
    
    int **data = new int*[height];
    for (int i = 0; i < height; i++) {
        data[i] = new int[width];
        memset(data[i], 0, width * sizeof(int));
    }
    printf("Write array:\n");
    for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
            scanf(" %d", &data[i][j]);
        }
    }
    
    bool found = false;
    printf("Saddle points found:\n");
    for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
            int minInRow = findMinInRow(data, height, width, i);
            int maxInColumn = findMaxInColumn(data, height, width, j);
            if (data[i][j] == minInRow && data[i][j] == maxInColumn) {
                found = true;
                printf("%d at position %d, %d\n", data[i][j], i + 1, j + 1);
            }
        }
    }
    if (!found) {
        printf("No saddle points found\n");
    }
    
    for (int i = 0; i < height; i++)
        delete data[i];
    delete[] data;
    return 0;
}

