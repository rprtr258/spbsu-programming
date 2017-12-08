#include <stdio.h>

int fib(const int index) {
    int f1 = 0, f2 = 1;
    for (int i = 0; i < index; i++) {
        int f3 = f1 + f2;
        f1 = f2;
        f2 = f3;
    }
    return f2;
}

int main() {
    printf("Write index of fib number: ");
    int i;
    scanf("%d", &i);
    printf("%d\n", fib(i));
    return 0;
}
