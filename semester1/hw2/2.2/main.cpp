#include <stdio.h>

int pow(const int &base, const int &degree) {
    if (degree == 0)
        return 1;
    if (degree == 1)
        return base;
    if (degree == 2)
        return base * base;
    if (degree % 2 == 1)
        return base * pow(pow(base, degree / 2), 2);
    // degree % 2 == 0
    return pow(pow(base, degree / 2), 2);
}

int main() {
    printf("Write base and degree:\n");
    int base = 0;
    int degree = 0;
    scanf("%d %d", &base, &degree);
    int result = pow(base, degree);
    printf("%d**%d = %d", base, degree, result);
    return 0;
}