#include <stdio.h>

using namespace std;

int main() {
    int a = 0;
    int b = 0;
    printf("Write a, b to calculate a // b: ");
    scanf("%d %d", &a, &b);
    if (b < 0) {
        b = -b;
        a = -a;
    }
    
    int q = 0;
    while (a >= b) {
        a -= b;
        q++;
    }
    while (a < 0) {
        a += b;
        q--;
    }
    printf("Result: %d", q);
    return 0;
}