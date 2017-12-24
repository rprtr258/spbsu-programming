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

State getNextState(State const curState, char const symbol) {
    switch (curState) {
        case initState: {
            if (isSignChar(symbol))
                return signState;
            else
                return (isdigit(symbol) ? integerPartState : error);
        }
        case signState: {
            return (isdigit(symbol) ? integerPartState : error);
        }
        case integerPartState: {
            if (symbol == '.')
                return dotState;
            else if (symbol == 'E')
                return exponentSymbolState;
            else
                return (isdigit(symbol) ? integerPartState : error);
        }
        case dotState: {
            return (isdigit(symbol) ? fractPartState : error);
        }
        case fractPartState: {
            if (symbol == 'E')
                return exponentSymbolState;
            else
                return (isdigit(symbol) ? fractPartState : error);
        }
        case exponentSymbolState: {
            if (isSignChar(symbol))
                return exponentSignState;
            else
                return (isdigit(symbol) ? exponentState : error);
        }
        case exponentSignState: {
            return (isdigit(symbol) ? exponentState : error);
        }
        case exponentState: {
            return (isdigit(symbol) ? exponentState : error);
        }
    }
}

bool matchesRegex(const char *string) {
    if (string == nullptr)
        return false;
    
    State curState = initState;
    for (int i = 0; string[i] != '\0'; i++) {
        char symbol = string[i];
        curState = getNextState(curState, symbol);
    }
    return isTerminalState(curState);
}
