#include <stdio.h>
#include <ctype.h>
#include <string.h>
#include <algorithm>

int countSpaces(char const *str, const size_t len) {
    int result = 0;
    for (size_t i = 0; i < len; i++)
        if (str[i] == ' ')
            result++;
    return result;
}

void strConcate(char *str, int &j, const char *token) {
    size_t len = strlen(token);
    for (size_t i = 0; i < len; i++) {
        str[j] = token[i];
        j++;
    }
}

int main() {
    printf("Beautiful polynom\n");
    
    printf("Write coefficients of polynom in order an, ..., a1, a0 in one line,\n");
    printf("where pol(x) = an*x^n + ... + a1 * x + a0\n");
    char str[1000];
    gets(str);
    size_t len = strlen(str);
    
    char ansRow1[1000];
    int j1 = 0;
    char ansRow2[1000];
    int j2 = 0;
    int degree = countSpaces(str, len);
    for (size_t i = 0; i < len; i++) {
        if (str[i] == '-') {
            strConcate(ansRow1, j1, " ");
            strConcate(ansRow2, j2, "-");
            if (i > 0) {
                strConcate(ansRow1, j1, " ");
                strConcate(ansRow2, j2, " ");
            }
        } else if (str[i] == '0') {
            i++; // skip
            degree--;
        } else if (str[i] == '1') {
            if (str[i + 1] != ' ' && str[i + 1] != '\0' || i > 0 && isdigit(str[i - 1])) {
                strConcate(ansRow1, j1, " ");
                strConcate(ansRow2, j2, "1");
            }
        } else if ('2' <= str[i] && str[i] <= '9') {
            strConcate(ansRow1, j1, " ");
            ansRow2[j2] = str[i];
            j2++;
        } else if (str[i] == ' ') {
            strConcate(ansRow1, j1, " ");
            strConcate(ansRow2, j2, "x");
            char num[100], spaces[100];
            int p = 0;
            int deg = degree;
            while (deg) {
                num[p] = '0' + deg % 10;
                spaces[p] = ' ';
                deg /= 10;
                p++;
            }
            if (degree == 1)
                num[0] = ' ';
            std::reverse(num, num + p);
            num[p] = '\0';
            spaces[p] = '\0';
            strConcate(ansRow1, j1, num);
            strConcate(ansRow2, j2, spaces);
            degree--;
            if (str[i + 1] != '-' && str[i + 1] != '0') {
                strConcate(ansRow1, j1, "  ");
                strConcate(ansRow2, j2, "+ ");
            }
        }
    }
    ansRow1[j1] = '\0';
    ansRow2[j2] = '\0';
    printf("Beautiful polynome:\n");
    printf("%s\n", ansRow1);
    printf("%s", ansRow2);
    return 0;
}