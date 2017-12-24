#include <ctype.h>
#include "regex.h"

enum State {
    initState, signState, integerPartState, dotState, fractPartState, exponentSymbolState, exponentSignState, exponentState, error
};

bool isTerminalState(State const state) {
    return (state == integerPartState || state == fractPartState || state == exponentState);
}

bool isSignChar(char const symbol) {
    return (symbol == '+' || symbol == '-');
}

bool matchesRegex(const char *string) {
    if (string == nullptr)
        return false;
    
    State curState = initState;
    for (int i = 0; string[i] != '\0'; i++) {
        char symbol = string[i];
        switch (curState) {
            case initState: {
                if (isSignChar(symbol))
                    curState = signState;
                else if (isdigit(symbol))
                    curState = integerPartState;
                else
                    curState = error;
                break;
            }
            case signState: {
                if (isdigit(symbol))
                    curState = integerPartState;
                else
                    curState = error;
                break;
            }
            case integerPartState: {
                if (isdigit(symbol))
                    curState = integerPartState;
                else if (symbol == '.')
                    curState = dotState;
                else if (symbol == 'E')
                    curState = exponentSymbolState;
                else
                    curState = error;
                break;
            }
            case dotState: {
                if (isdigit(symbol))
                    curState = fractPartState;
                else
                    curState = error;
                break;
            }
            case fractPartState: {
                if (isdigit(symbol))
                    curState = fractPartState;
                else if (symbol == 'E')
                    curState = exponentSymbolState;
                else
                    curState = error;
                break;
            }
            case exponentSymbolState: {
                if (isSignChar(symbol))
                    curState = exponentSignState;
                else if (isdigit(symbol))
                    curState = exponentState;
                else
                    curState = error;
                break;
            }
            case exponentSignState: {
                if (isdigit(symbol))
                    curState = exponentState;
                else
                    curState = error;
                break;
            }
            case exponentState: {
                if (isdigit(symbol))
                    curState = exponentState;
                else
                    curState = error;
                break;
            }
        }
    }
    return isTerminalState(curState);
}
