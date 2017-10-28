#include <stdio.h>
#include <utility>

void reverse(int *array, const int left, const int right) {
    int i = left;
    int j = right;
    while (i < j) {
        std::swap(array[i], array[j]);
        i++;
        j--;
    }
}

int main() {
    int sizeM = 0;
    int sizeN = 0;
    printf("Write m, n(sizes of array parts): ");
    scanf("%d %d", &sizeM, &sizeN);
    
    printf("Write array with %d elements which is array of size %d followed by array of size %d:\n", sizeM + sizeN, sizeM, sizeN);
    int *arrayX = new int[sizeM + sizeN];
    for (int i = 0; i < sizeM + sizeN; i++)
        scanf("%d", &arrayX[i]);
    
    reverse(arrayX, 0, sizeM - 1);
    reverse(arrayX, sizeM, sizeM + sizeN - 1);
    reverse(arrayX, 0, sizeM + sizeN - 1);
    
    printf("Swapped arrays:\n");
    for (int i = 0; i < sizeN + sizeM; i++)
        printf("%d ", arrayX[i]);
    
    delete[] arrayX;
    return 0;
}