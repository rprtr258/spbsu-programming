#include <stdio.h>

void recursive(const int m1, const int n1, const int m2, const int n2, const int number) {
    if (n1 > number || n2 > number)
        return;
    
    recursive(m1, n1, m1 + m2, n1 + n2, number);
    
    if (n1 + n2 <= number) {
        printf("%d/%d\n", m1 + m2, n1 + n2);
    }
    
    recursive(m1 + m2, n1 + n2, m2, n2, number);
}

int main() {
    printf("Program that prints all fractions m/n in (0; 1) where n <= N\n");
    
    int number = 0;
    printf("Write N: ");
    scanf("%d", &number);
    
    printf("Fractions:\n");
    recursive(0, 1, 1, 1, number);
    return 0;
}