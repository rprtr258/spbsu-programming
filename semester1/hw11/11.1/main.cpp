#include <stdio.h>
#include <string.h>
#include "regex.h"

void addChar(char *&string, char const symbol) {
    int stringLength = strlen(string);
    char *newString = new char[stringLength + 2];
    strncpy(newString, string, stringLength + 2);
    newString[stringLength] = symbol;
    newString[stringLength + 1] = '\0';
    delete[] string;
    string = newString;
}

char* readString() {
    char *result = new char[1];
    result[0] = '\0';
    char symbol = '\0';
    symbol = getchar();
    while (symbol != '\n') {
        addChar(result, symbol);
        symbol = getchar();
    }
    return result;
}

int main() {
    printf("Programs that checks whether string is real number\n");
    
    printf("Write string:\n");
    char *string = readString();
    if (matchesRegex(string))
        printf("It is real number\n");
    else
        printf("It isn't real number\n");
    
    delete[] string;
    return 0;
}
