#include <stdio.h>

void printArray(int *array, const int arraySize) {
    if (arraySize == 1)
        return;
    
    for (int i = 0; i < arraySize; i++) {
        printf("%d", array[i]);
        if (i < arraySize - 1)
            printf(" + ");
    }
    printf("\n");
}


void recursive(const int number, int *array, const int minx = 1, int pos = 0) {
    if (number == 0) {
        printArray(array, pos);
        return;
    }
    for (int i = minx; i <= number; i++) {
        array[pos] = i;
        recursive(number - i, array, i, pos + 1);
    }
}

int main() {
    printf("Program that prints N as sum of integers\n");
    
    int number = 0;
    printf("Write N: ");
    scanf("%d", &number);
    int *array = new int[number];
    
    printf("Decompositions:\n");
    recursive(number, array);
    
    delete[] array;
    return 0;
}