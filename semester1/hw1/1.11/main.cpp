#include <stdio.h>
#include <utility>

void quickSort(int *array, const int &left, const int &right) {
    if (right <= left)
        return;
    
    const int midVal = array[left];
    int i = left;
    int j = right;
    while (i < j) {
        while (i < right && array[i] < midVal)
            i++;
        while (j > left && array[j] >= midVal)
            j--;
        
        if (i <= j) {
            std::swap(array[i], array[j]);
            i++;
            j--;
        }
    }
    
    quickSort(array, left, j);
    quickSort(array, i, right);
}

int main() {
    int arraySize = 0;
    printf("Write size of array you want to sort: ");
    scanf("%d", &arraySize);
    
    int *array = new int[arraySize];
    printf("Write array with %d elements:\n", arraySize);
    for (int i = 0; i < arraySize; i++)
        scanf("%d", &array[i]);

    quickSort(array, 0, arraySize - 1);

    printf("Sorted array:\n");
    for (int i = 0; i < arraySize; i++)
        printf("%d ", array[i]);
    
    delete[] array;
    return 0;
}