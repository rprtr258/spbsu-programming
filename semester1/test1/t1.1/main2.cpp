#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <string.h>
#include <utility>

// random integer from range [a, b], 0 < a <= b
int randRange(int const a, int const b) {
    return (rand() % (b - a + 1) + a);
}

void sortEven(int* const array, const int arraySize) {
    for (int i = 2; i < arraySize; i += 2) {
        int j = i;
        while (j > 0 && array[j - 2] > array[j]) {
            std::swap(array[j - 2], array[j]);
            j -= 2;
        }
    }
}

bool testRandRange() {
    for (int i = 0; i < 1000000; i++) {
        int x = randRange(11, 42);
        if (x < 11 || 42 < x)
            return false;
    }
    return true;
}

bool testEvenSort() {
    int array[10] = {8, 1, 6, 3, 4, 5, 2, 7, 0, 9};
    sortEven(array, 10);
    for (int i = 0; i < 10; i++)
        if (array[i] != i)
            return false;
    return true;
}

bool test() {
    if (!testRandRange()) {
        printf("Error in randRange function\n");
        return false;
    }
    if (!testEvenSort()) {
        printf("Error in sortEven function\n");
        return false;
    }
    return true;
}

int main() {
    if (!test())
        return 0;
    printf("This program sorts even positions in array\n");
    
    int arraySize = 0;
    printf("Write size of array: ");
    scanf("%d", &arraySize);
    int *array = new int[arraySize];
    printf("Write type of array (\"rand\" fo random with values 11-42 or \"hand\" to write it) without quotes:\n");
    char s[5];
    scanf("%4s", s);
    if (strcmp(s, "rand") == 0) {
        srand(time(nullptr));
        printf("Generated array:\n");
        for (int i = 0; i < arraySize; i++) {
           array[i] = randRange(11, 42);
           printf("%d ", array[i]);
        }
        printf("\n");
    } else if (strcmp(s, "hand") == 0) {
        printf("Write your array of size %d:\n", arraySize);
        for (int i = 0; i < arraySize; i++)
            scanf("%d", &array[i]);
    } else {
        printf("Sorry i couldn\'t get it");
        delete[] array;
        return 0;
    }
    
    sortEven(array, arraySize);
    
    printf("Sorted array:\n");
    for (int i = 0; i < arraySize; i++)
        printf("%d ", array[i]);
    
    delete[] array;
    return 0;
}