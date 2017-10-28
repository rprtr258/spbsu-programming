#include <stdio.h>

using namespace std;

int main() {
    int x = 0;
    printf("Write x in order to make this program be able to calculate x**4 + x**3 + x**2 + x + 1: ");
    scanf("%d", &x);
    int x2 = x * x;
    int f = (x2 + x) * (x2 + 1) + 1;
    printf("Result: %d", f);
    return 0;
}