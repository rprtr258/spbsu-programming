#include <stdio.h>
#include <string.h>
#include <stack>

bool isOpening(const char bracket) {
    return (bracket == '(' || bracket == '[' || bracket == '{');
}

bool isClosing(const char bracket) {
    return (bracket == ')' || bracket == ']' || bracket == '}');
}

bool isRelevant(const char bracketOpened, const char bracketClosed) {
    if (bracketOpened == '(' && bracketClosed == ')')
        return true;
    if (bracketOpened == '[' && bracketClosed == ']')
        return true;
    if (bracketOpened == '{' && bracketClosed == '}')
        return true;

    return false;
}

bool isBracket(const char symbol) {
    return (isOpening(symbol) || isClosing(symbol));
}

int main() {
    printf("Checker of syntax\n");
    
    char str[100];
    int strLength = -1;
    printf("Write your text:\n");
    gets(str);
    strLength = strlen(str);
    
    std::stack<char> opened;
    bool error = false;
    
    for (int i = 0; i < strLength; i++) {
        if (isBracket(str[i])) {
            if (isOpening(str[i])) { // opened ([{
                opened.push(str[i]);
            } else { // closed )]}
                if (!opened.empty() && isRelevant(opened.top(), str[i])) {
                    opened.pop();
                } else {
                    error = true;
                    break;
                }
            }
        }
    }
    
    if (!opened.empty())
        error = true;
    
    if (error)
        printf("Sequence is not correct");
    else
        printf("Sequence is correct");
        
    return 0;
}