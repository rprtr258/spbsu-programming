#include <stdio.h>
#include "../quicksort/quicksort.h"

bool testSort() {
    bool result = true;
    
    int array[5] = {4, 2, 1, 3, 5};
    quickSort(array, 5);
    for (int unsigned i = 0; i < 5; i++)
        result &= (array[i] == (int)i + 1);
    
    return result;
}

bool testSort2() {
    bool result = true;
    
    int *array = new int[5];
    int tmp[] = {4, 2, 1, 3, 5};
    for (int unsigned i = 0; i < 5; i++)
        array[i] = tmp[i];
    quickSort(array, 5);
    for (int unsigned i = 0; i < 5; i++)
        result &= (array[i] == (int)i + 1);
    delete[] array;
    
    return result;
}

bool testOne() {
    bool result = true;
    
    int array[] = {4};
    quickSort(array, 1);
    result &= (array[0] == 4);
    
    return result;
}

bool testTwo() {
    bool result = true;
    
    int array[2] = {1, 0};
    quickSort(array, 2);
    for (int unsigned i = 0; i < 2; i++)
        result &= (array[i] == (int)i);
    
    return result;
}

int main() {
    printf("Something\n");
    if (!testOne()) {
        printf("One test failed\n");
    }
    if (!testTwo()) {
        printf("Two test failed\n");
    }
    if (!testSort()) {
        printf("Sort test failed\n");
    }
    if (!testSort2()) {
        printf("Sort2 test failed\n");
    }
    return 0;
}
