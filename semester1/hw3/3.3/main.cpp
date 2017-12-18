#include <stdio.h>
#include <algorithm>
#include <string.h>

int main() {
    printf("Program that reorder digits of your number to make smallest possible number.\n");
    
    char number[101];
    int length = 0;
    printf("Write number: ");
    scanf("%100s", number);
    length = strlen(number);
    
    const int digits = 10;
    int digitCount[digits];
    for (int i = 0; i < digits; i++)
        digitCount[i] = 0;
    
    for (int i = 0; i < length; i++)
        digitCount[number[i] - '0']++;
        
    int smallestDigit = digits;
    for (int i = 0; i < length; i++)
        if (number[i] != '0')
            smallestDigit = std::min(smallestDigit, number[i] - '0');
    
    printf("Result: ");
    if (smallestDigit < digits)
        printf("%d", smallestDigit);
    digitCount[smallestDigit]--;
    for (int i = 0; i < digits; i++)
        for (int j = 0; j < digitCount[i]; j++)
            printf("%d", i);
    
    return 0;
}