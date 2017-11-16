#include <string.h>
#include <ctype.h>
#include <algorithm>
#include "polynom.h"

struct CutePolynom;

int countSpaces(char const *str);
void strConcate(char *str, int &j, const char *token);
bool isCorrectNumberBegin(char *str, int i);
bool isEndOfToken(char *str, int i);
void skipToNextToken(char *str, CutePolynom *tmp);
void writeXDegree(CutePolynom *tmp);
void writeCoefficient(char *str, CutePolynom *tmp);

struct CutePolynom {
    char *row1 = nullptr;
    int j1 = 0;
    char *row2 = nullptr;
    int j2 = 0;
    int degree = 0;
    int i = 0;
    bool isFirstToken = true;
};

void beautify(char *str, char *ansRow1, char *ansRow2) {
    CutePolynom *tmp = new CutePolynom();
    tmp->degree = countSpaces(str);
    tmp->row1 = ansRow1;
    tmp->row2 = ansRow2;
    
    while (str[tmp->i] != '\0') {
        if (str[tmp->i] == '-' && isCorrectNumberBegin(str, tmp->i)) {
            writeCoefficient(str, tmp);
            writeXDegree(tmp);
            tmp->isFirstToken = false;
        } else if (isdigit(str[tmp->i]) && str[tmp->i] != '0') {
            writeCoefficient(str, tmp);
            writeXDegree(tmp);
            tmp->isFirstToken = false;
        }
        skipToNextToken(str, tmp);
    }
    // case if all zeroes
    if (tmp->j1 == 0 && tmp->j2 == 0) {
        strConcate(tmp->row1, tmp->j1, " ");
        strConcate(tmp->row2, tmp->j2, "0");
    }
    tmp->row1[tmp->j1] = '\0';
    tmp->row2[tmp->j2] = '\0';
    
    delete tmp;
}

int countSpaces(char const *str) {
    int result = 0;
    int len = strlen(str);
    for (int i = 0; i < len; i++)
        if (str[i] == ' ')
            result++;
    return result;
}

void strConcate(char *str, int &j, const char *token) {
    int len = strlen(token);
    for (int i = 0; i < len; i++) {
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

void skipToNextToken(char *str, CutePolynom *tmp) {
    while (!isEndOfToken(str, tmp->i))
        tmp->i++;
    if (str[tmp->i] != '\0') {
        tmp->i++;
        tmp->degree--;
    }
}

void writeXDegree(CutePolynom *tmp) {
    if (tmp->degree == 0)
        return;
    
    strConcate(tmp->row1, tmp->j1, " ");
    strConcate(tmp->row2, tmp->j2, "x");
    
    int degree = tmp->degree;
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
        strConcate(tmp->row1, tmp->j1, num);
        strConcate(tmp->row2, tmp->j2, spaces);
    }
}

void writeCoefficient(char *str, CutePolynom *tmp) {
    int i = tmp->i;
    if (!tmp->isFirstToken) {
        char op[4] = " + ";
        if (str[i] == '-') {
            i++;
            op[1] = '-';
        }
        strConcate(tmp->row1, tmp->j1, "   ");
        strConcate(tmp->row2, tmp->j2, op);
    } else if (str[i] == '-') {
        strConcate(tmp->row1, tmp->j1, " ");
        strConcate(tmp->row2, tmp->j2, "-");
        i++;
    }
    if (tmp->degree > 0 && str[i] == '1' && isEndOfToken(str, i + 1)) // coeff. is 1
        return;
    char digit[2] = "$";
    while (!isEndOfToken(str, i)) {
        digit[0] = str[i];
        strConcate(tmp->row1, tmp->j1, " ");
        strConcate(tmp->row2, tmp->j2, digit);
        i++;
    }
}
