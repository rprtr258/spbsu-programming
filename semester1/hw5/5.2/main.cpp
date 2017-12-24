#include <stdio.h>

void printBinary(const int unsigned x) {
    int unsigned bit = 0x800000;
    while (bit) {
        printf((x & bit) ? "1" : "0");
        bit >>= 1;
    }
    printf("\n");
}

int main() {
    printf("Complement code test\n");
    
    printf("Write two numbers from -2\'147\'483\'647 to 2\'147\'483\'648 inclusive\n");
    int first = 0;
    int second = 0;
    scanf("%d %d", &first, &second);
    
    int unsigned a = first;
    int unsigned b = second;
    
    printf("Complement code:\n");
    printBinary(a);
    printBinary(b);
    
    int unsigned sum = a + b;
    printf("Complement code of sum:\n");
    printBinary(sum);
    bool bit = (sum & 0x80000000);
    int sumNormal = (bit ? (~sum) + 1 : sum);
    printf("Decimal sum:\n");
    if (bit)
        printf("-");
    printf("%d", sumNormal);
    return 0;
}
