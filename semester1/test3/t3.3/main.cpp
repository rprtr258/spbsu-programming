#include <stdio.h>

enum State {
    st1, st2, st3, st4, st5, st63, noMatch
};

/*
 * L0 is [A-Z]
 * L1 is [A-Z0-9._%+-]
 * L2 is [A-Z0-9-]
 */

bool isL0(char const symbol) {
    return ('A' <= symbol && symbol <= 'Z');
}

bool isL1(char const symbol) {
    return ('A' <= symbol && symbol <= 'Z' ||
            '0' <= symbol && symbol <= '9' ||
            symbol == '.' ||
            symbol == '_' ||
            symbol == '%' ||
            symbol == '+' ||
            symbol == '-');
}

bool isL2(char const symbol) {
    return ('A' <= symbol && symbol <= 'Z' ||
            '0' <= symbol && symbol <= '9' ||
            symbol == '-');
}

int main() {
    printf("Regex matcher for pattern \"[A-Z0-9._%%+-]+@([A-Z0-9-]+\\.)+[A-Z]\"\n");
    
    printf("Write your string:\n");
    State curState = st1;
    char symbol = '\n';
    scanf(" %c", &symbol);
    while (symbol != '\n') {
        switch (curState) {
            case st1: {
                if (isL1(symbol)) {
                    curState = st2;
                } else {
                    curState = noMatch;
                }
                break;
            }
            case st2: {
                if (isL1(symbol)) {
                    curState = st2;
                } else if (symbol == '@') {
                    curState = st3;
                } else {
                    curState = noMatch;
                }
                break;
            }
            case st3: {
                if (isL2(symbol)) {
                    curState = st4;
                } else {
                    curState = noMatch;
                }
                break;
            }
            case st4: {
                if (isL2(symbol)) {
                    curState = st4;
                } else if (symbol == '.') {
                    curState = st5;
                } else {
                    curState = noMatch;
                }
                break;
            }
            case st5: {
                if (isL0(symbol)) {
                    curState = st63;
                } else if (isL2(symbol)) {
                    curState = st4;
                } else {
                    curState = noMatch;
                }
                break;
            }
            case st63: {
                if (isL2(symbol)) {
                    curState = st4;
                } else if (symbol == '.') {
                    curState = st5;
                } else {
                    curState = noMatch;
                }
                break;
            }
        }
        scanf("%c", &symbol);
    }
    if (curState == st63) {
        printf("Your string matches that regex\n");
    } else {
        printf("Your string doesn't match that regex\n");
    }
    return 0;
}

