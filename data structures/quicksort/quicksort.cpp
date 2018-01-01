#include <utility>
#include "quicksort.h"

void quickSort(int *array, int const left, int const right) {
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

void quickSort(int *array, int unsigned const size) {
    if (size == 0 || array == nullptr)
        return;
    
    quickSort(array, 0, size - 1);
}

