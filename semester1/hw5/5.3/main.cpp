#include <stdio.h>
#include <ctype.h>
#include <string.h>
#include <algorithm>

int countSpaces(char const *str, const size_t len);
void strConcate(char *str, int &j, const char *token);
bool isCorrectNumberBegin(char *str, int i);
bool isEndOfToken(char *str, int i);
void skipToNextToken(char *str, int &i, int &degree);
void writeXDegree(char *ansRow1, int &j1, char *ansRow2, int &j2, int degree);
void writeCoefficient(char *ansRow1, int &j1, char *ansRow2, int &j2, char *str, int i, int degree, bool isFirstToken);

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

bool isCorrectNumberBegin(char *str, int i) {
    return ((str[i] == '-' && isdigit(str[i + 1]) && str[i + 1] != '0') || (isdigit(str[i]) && str[i] != '0'));
}

bool isEndOfToken(char *str, int i) {
    return (str[i] == ' ' || str[i] == '\0');
}

void skipToNextToken(char *str, int &i, int &degree) {
    while (!isEndOfToken(str, i))
        i++;
    if (str[i] != '\0') {
        i++;
        degree--;
    }
}

void writeXDegree(char *ansRow1, int &j1, char *ansRow2, int &j2, int degree) {
    if (degree == 0)
        return;
    
    strConcate(ansRow1, j1, " ");
    strConcate(ansRow2, j2, "x");
    
    if (degree > 1) {
        char num[100], spaces[100];
        int p = 0;
        while (degree) {
            num[p] = '0' + degree % 10;
            spaces[p] = ' ';
            degree /= 10;
            p++;
        }
        std::reverse(num, num + p);
        num[p] = '\0';
        spaces[p] = '\0';
        strConcate(ansRow1, j1, num);
        strConcate(ansRow2, j2, spaces);
    }
}

void writeCoefficient(char *ansRow1, int &j1, char *ansRow2, int &j2, char *str, int i, int degree, bool isFirstToken) {
    if (!isFirstToken) {
        char op[4] = " + ";
        if (str[i] == '-') {
            i++;
            op[1] = '-';
        }
        strConcate(ansRow1, j1, "   ");
        strConcate(ansRow2, j2, op);
    } else if (str[i] == '-') {
        strConcate(ansRow1, j1, " ");
        strConcate(ansRow2, j2, "-");
        i++;
    }
    if (degree > 0 && str[i] == '1' && isEndOfToken(str, i + 1)) // coeff. is 1
        return;
    char tmp[2] = "$";
    while (!isEndOfToken(str, i)) {
        tmp[0] = str[i];
        strConcate(ansRow1, j1, " ");
        strConcate(ansRow2, j2, tmp);
        i++;
    }
}

// TODO: move all shit to module
// TODO: signs between tokens

int main() {
    printf("Beautiful polynom\n");
    
    printf("Write coefficients of polynom as array \"an ... a1 a0\" in one line,\n");
    printf("where pol(x) = an * x ^ n + ... + a1 * x + a0\n");
    char str[1000];
    gets(str);
    size_t len = strlen(str);
    
    char ansRow1[1000];
    int j1 = 0;
    char ansRow2[1000];
    int j2 = 0;
    int degree = countSpaces(str, len);
    int i = 0;
    bool isFirstToken = true;
    while (str[i] != '\0') {
        // printf("%c %d\n", str[i], degree);
        // checking for first char of token
        if (str[i] == '-' && isCorrectNumberBegin(str, i)) {
            // -C * x ^ deg 
            writeCoefficient(ansRow1, j1, ansRow2, j2, str, i, degree, isFirstToken);
            writeXDegree(ansRow1, j1, ansRow2, j2, degree);
            isFirstToken = false;
        } else if (isdigit(str[i]) && str[i] != '0') { // nor "0" neither "1"
            // copy coefficient and x with degree
            writeCoefficient(ansRow1, j1, ansRow2, j2, str, i, degree, isFirstToken);
            writeXDegree(ansRow1, j1, ansRow2, j2, degree);
            isFirstToken = false;
        }
        skipToNextToken(str, i, degree);
    }
    // case if all zeroes
    if (j1 == 0 && j2 == 0) {
        strConcate(ansRow1, j1, " ");
        strConcate(ansRow2, j2, "0");
    }
    ansRow1[j1] = '\0';
    ansRow2[j2] = '\0';
    printf("Beautiful polynome:\n");
    printf("%s\n", ansRow1);
    printf("%s", ansRow2);
    return 0;
}