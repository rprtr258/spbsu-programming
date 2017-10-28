#include <stdio.h>

int main() {
    const int maxDigit = 9;
    const int maxDigitSum = maxDigit + maxDigit + maxDigit;
    int count[maxDigitSum + 1];
    for (int s = 0; s <= maxDigitSum; s++)
        count[s] = 0;
    
    for (int d1 = 0; d1 <= maxDigit; d1++)
        for (int d2 = 0; d2 <= maxDigit; d2++)
            for (int d3 = 0; d3 <= maxDigit; d3++)
                count[d1 + d2 + d3]++;
            
    int answer = 0;
    for (int s = 0; s <= maxDigitSum; s++)
        answer += count[s] * count[s];
    
    printf("There is %d lucky tickets.", answer);
    return 0;
}