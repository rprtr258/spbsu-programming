#include <stdio.h>
#include "heap.h"

void printArray(const int *array, const int arraySize) {
    for (int i = 0; i < arraySize; i++) {
        printf("%d", array[i]);
        if (i < arraySize - 1)
            printf(" ");
    }
}

int main() {
    printf("Program that sort array of integers using heapsort\n");
    
    int arrSize = 0;
    printf("Write size of array: ");
    scanf("%d", &arrSize);
    
    int *array = new int[arrSize];
    printf("Write %d elements of array:\n", arrSize);
    for (int i = 0; i < arrSize; i++)
        scanf("%d", &array[i]);
    
    heapSort(array, arrSize);
    
    printf("Sorted array:\n");
    printArray(array, arrSize);
    
    delete[] array;
    return 0;
}
