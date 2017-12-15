#include <stdio.h>
#include <string.h>

void addToArray(int *&array, int &size, int const value) {
    int *newArray = new int[size + 1];
    if (array != nullptr) {
        memcpy(newArray, array, size * sizeof(int));
        delete[] array;
    }
    newArray[size] = value;
    size++;
    array = newArray;
}

int* readArray(int &arraySize) {
    int value = 0;
    int *result = nullptr;
    
    scanf(" %d", &value);
    while (value != 0) {
        addToArray(result, arraySize, value);
        scanf(" %d", &value);
    }
    
    return result;
}

void sortArray(int *data, int const dataSize, int *&sortedArray, int *&occurences, int &uniques) {
    if (data == nullptr)
        return;
    
    int *tempData = new int[dataSize];
    memcpy(tempData, data, dataSize * sizeof(int));
    
    for (int i = 0; i < dataSize; i++) {
        for (int j = 0; j < i; j++) {
            if (tempData[j] > tempData[i]) {
                int temp = tempData[j];
                tempData[j] = tempData[i];
                tempData[i] = temp;
            }
        }
    }
    
    for (int i = 0; i < dataSize; i++) {
        if (i == 0 || tempData[i] != tempData[i - 1]) {
            addToArray(sortedArray, uniques, tempData[i]);
            uniques--;
            addToArray(occurences, uniques, 1);
        } else {
            occurences[uniques - 1]++;
        }
    }
    
    delete[] tempData;
}

int main() {
    printf("Sort array of numbers v3000\n");
    
    printf("Write array of numbers(0 to end input):\n");
    int dataSize = 0;
    int *data = readArray(dataSize);
    
    int *sortedArray = nullptr;
    int *occurences = nullptr;
    int uniques = 0;
    sortArray(data, dataSize, sortedArray, occurences, uniques);
    
    printf("Sorted array(number[count]):\n");
    for (int i = 0; i < uniques; i++)
        printf("%d[%d] ", sortedArray[i], occurences[i]);
    printf("\n");
    
    if (data != nullptr)
        delete[] data;
    if (sortedArray != nullptr)
        delete[] sortedArray;
    if (occurences != nullptr)
        delete[] occurences;
    return 0;
}

