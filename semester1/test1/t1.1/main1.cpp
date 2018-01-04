#include <stdio.h>

// Time: O(N) + O(N) = O(N)
void merge(int *result, int *arr1, const int len1, int *arr2, const int len2) {
    int i1 = 0; // pointer in arr1
    int i2 = 0; // pointer in arr2
    int j = 0; //pointer in result
    while (i1 < len1 && i2 < len2) { // Time: O(min(len1, len2)) = O(N)
        if (arr1[i1] > arr2[i2]) {
            result[j] = arr1[i1];
            j++;
            i1++;
        } else {
            result[j] = arr2[i2];
            j++;
            i2++;
        }
    }
    if (i1 < len1) { // remain in arr1
        while (i1 < len1) { // Time: O(len1 - min(len1, len2)) = O(N)
            result[j] = arr1[i1];
            j++;
            i1++;
        }
    } else if (i2 < len2) { // remain in arr2
        while (i2 < len2) { // Time: O(len2 - min(len1, len2)) = O(N)
            result[j] = arr2[i2];
            j++;
            i2++;
        }
    }
}

int main() {
    printf("Sequences merger in non-increasing order\n");
    
    int lenVasya = -1;
    printf("Vasya, write length of your pile:\n");
    scanf("%d", &lenVasya);
    int *arrayVasya = new int[lenVasya];
    printf("Vasya, write your sequence:\n");
    for (int i = 0; i < lenVasya; i++)
        scanf("%d", &arrayVasya[i]);
    
    int lenPetya = -1;
    printf("Petya, write length of your pile:\n");
    scanf("%d", &lenPetya);
    int *arrayPetya = new int[lenPetya];
    printf("Petya, write your sequence:\n");
    for (int i = 0; i < lenPetya; i++)
        scanf("%d", &arrayPetya[i]);
        
    int lenResult = lenVasya + lenPetya;
    int *arrayResult = new int[lenResult];
    merge(arrayResult, arrayVasya, lenVasya, arrayPetya, lenPetya);
    
    printf("Merged sequence:\n");
    for (int i = 0; i < lenResult; i++)
        printf("%d ", arrayResult[i]);
    
    delete[] arrayVasya;
    delete[] arrayPetya;
    delete[] arrayResult;
    return 0;
}