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

inline bool isCorrectNumberBegin(char *str, int i) {
    return ((str[i] == '-' && isdigit(str[i]) && str[i + 1] != '0') || (isdigit(str[i]) && str[i] != '0'));
}

inline bool isEndOfToken(chaar *str, int i) {
    return (str[i] == ' ' || str[i] == '\0');
}

// TODO: move all shit to module

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
    size_t i = 0;
    while (i < len && degree > 0) {
        // checking for first char of token
        if (str[i] == '-') {
            if (isCorrectNumberBegin(str, i)) {
                // -1 * x ^ deg
                // or
                // -C * x ^ deg
                strConcate(ansRow1, j1, " ");
                strConcate(ansRow2, j2, "-");
                if (i > 0) {
                    strConcate(ansRow1, j1, " ");
                    strConcate(ansRow2, j2, " ");
                }
            } else {
                // skip till next token
                while (str[i + 1] != ' ' && str[i + 1] != '\0')
                    i++;
            }
        } else if (str[i] == '0') {
            // skip till next token
            if (i > 0) {
                i++; // skip
                degree--;
            } else {
                strConcate(ansRow1, j1, " ");
                strConcate(ansRow2, j2, "0");
            }
        } else if (str[i] == '1' && isEndOfToken(str, i + 1)) {
            // 1 * x ^ deg
            if ((str[i + 1] != ' ' && str[i + 1] != '\0') || (i > 0 && isdigit(str[i - 1]))) {
                strConcate(ansRow1, j1, " ");
                strConcate(ansRow2, j2, "1");
            }
        } else if (isdigit(str[i])) { // nor "0" neither "1"
            // copy coefficient and x with degree
            strConcate(ansRow1, j1, " ");
            ansRow2[j2] = str[i];
            j2++;
        } else if (str[i] == ' ') {
            // impossible
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
        i++;
    }
    // case if all zeroes
    ansRow1[j1] = '\0';
    ansRow2[j2] = '\0';
    printf("Beautiful polynome:\n");
    printf("%s\n", ansRow1);
    printf("%s", ansRow2);
    return 0;
}