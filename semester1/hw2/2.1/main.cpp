#include <stdio.h>
#include "matrix2x2.h"

using namespace std;

int calcFibonacci(const int index) {
    if (index == 0 || index == 1)
        return 1;
    return calcFibonacci(index - 2) + calcFibonacci(index - 1);
}

int iterFibonacci(const int index) {
    int f1 = 0, f2 = 1;
    for (int i = 0; i < index; i++) {
        int f3 = f1 + f2;
        f1 = f2;
        f2 = f3;
    }
    return f2;
}

int matrixFibonacci(const int index) {
    Matrix2x2 fib;
    matrixSet(fib, 1, 1, 1, 0);
    
    Matrix2x2 fibN = pow(fib, index);
    
    return fibN.data[0][0];
}

int main() {
    printf("Program that finds Fibonacci number by index\n");
    int index = 0;
    printf("Write index of Fibonacci number you want to find:\n");
    scanf("%d", &index);
    
    printf("%d-th Fibonacci number:\n", index);
    
    int matrixFib = matrixFibonacci(index);
    printf("Matrix: %d\n", matrixFib);
    
    int iterativeFib = iterFibonacci(index);
    printf("Iterative: %d\n", iterativeFib);
    
    int recursiveFib = calcFibonacci(index);
    printf("Recursive: %d\n", recursiveFib);
    
    return 0;
}
