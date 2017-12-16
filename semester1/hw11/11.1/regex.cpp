#include <ctype.h>
#include "regex.h"

enum State {
    st1, st2, st3, st4, st5, st6, st7, st8, error
};

bool isTerminalState(State const state) {
    return (state == st3 || state == st5 || state == st8);
}

bool isSignChar(char const symbol) {
    return (symbol == '+' || symbol == '-');
}

bool matchesRegex(const char *string) {
    State curState = st1;
    for (int i = 0; string[i] != '\0'; i++) {
        char symbol = string[i];
        switch (curState) {
            case st1: {
                if (isSignChar(symbol))
                    curState = st2;
                else if (isdigit(symbol))
                    curState = st3;
                else
                    curState = error;
                break;
            }
            case st2: {
                if (isdigit(symbol))
                    curState = st3;
                else
                    curState = error;
                break;
            }
            case st3: {
                if (isdigit(symbol))
                    curState = st3;
                else if (symbol == '.')
                    curState = st4;
                else if (symbol == 'E')
                    curState = st6;
                else
                    curState = error;
                break;
            }
            case st4: {
                if (isdigit(symbol))
                    curState = st5;
                else
                    curState = error;
                break;
            }
            case st5: {
                if (isdigit(symbol))
                    curState = st5;
                else if (symbol == 'E')
                    curState = st6;
                else
                    curState = error;
                break;
            }
            case st6: {
                if (isSignChar(symbol))
                    curState = st7;
                else if (isdigit(symbol))
                    curState = st8;
                else
                    curState = error;
                break;
            }
            case st7: {
                if (isdigit(symbol))
                    curState = st8;
                else
                    curState = error;
                break;
            }
            case st8: {
                if (isdigit(symbol))
                    curState = st8;
                else
                    curState = error;
                break;
            }
        }
    }
    return isTerminalState(curState);
}
