#include <stdio.h>
#include <algorithm>

int main() {
    printf("Program that finds maximum number in array which met at least 2 times\n");
    
    int arraySize = 0;
    printf("Write array size: ");
    scanf("%d", &arraySize);
    
    int *array = new int[arraySize];
    printf("Write array of size %d:\n", arraySize);
    for (int i = 0; i < arraySize; i++)
        scanf("%d", &array[i]);
    
    std::sort(array, array + arraySize);
    
    int pos = -1;
    for (int i = arraySize - 1; i > 0; i--) {
        if (array[i] == array[i - 1]) {
            pos = i;
            break;
        }
    }
    
    if (pos == -1) {
        printf("Maximum not found");
    } else {
        printf("Result is %d", array[pos]);
    }
    
    delete[] array;
    return 0;
}