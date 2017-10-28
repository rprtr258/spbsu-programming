#include <stdio.h>

int main() {
    int number = 0;
    printf("Write upper bound of primes you want to find:\n");
    scanf("%d", &number);

    bool *primality = new bool[number + 1];
    for (int i = 2; i <= number; i++)
        primality[i] = true;

    for (int p = 2; p <= number; p++) {
        if (primality[p]) {
            for (int i = p * p; i <= number; i += p)
                primality[i] = false;
        }
    }

    printf("List of primes equal or less than %d:\n", number);
    for (int i = 2; i <= number; i++)
        if (primality[i]) {
            if (i > 2)
                printf(", ");

            printf("%d", i);
        }

    delete[] primality;
    return 0;
}