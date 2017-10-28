#include <stdio.h>

int factorial(const int &n) {
    if (n < 2)
        return 1;
    
    return n * factorial(n - 1);
}

int iterativeFactorial(const int &n) {
    int result = 1;
    for (int i = 2; i <= n; i++)
        result *= i;
    return result;
}

int main() {
    int number;
    printf("Write N to calculate N!:\n");
    scanf("%d", &number);
    
    int recFactorial = factorial(number);
    int iterFactorial = iterativeFactorial(number);
    
    printf("Recursive: %d\n", recFactorial);
    printf("Iterative: %d\n", iterFactorial);
    
    return 0;
}