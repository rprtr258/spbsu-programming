#include <stdio.h>
#include <string.h>

void insertSpaces(char *str, int &strLength, int const pos, int const count) {
    for (int i = strLength; i >= pos + 1; i--) {
        str[i + count] = str[i];
    }
    for (int i = pos + 1; i <= pos + count; i++)
        str[i] = ' ';
    strLength += count;
}

int main() {
    printf("Program to insert spaces to format string into bigger length\n");
    
    printf("Write string:\n");
    char str[1000];
    gets(str);
    int strLength = strlen(str);
    int newLength = 0;
    printf("Write new length of string: ");
    scanf("%d", &newLength);
    
    int diff = newLength - strLength;
    int spaces = 0;
    for (int i = 0; i < strLength; i++)
        if (str[i] == ' ')
            spaces++;
    
    int remain = diff % spaces;
    int toInsert = diff / spaces;
    
    for (int i = 0; i < strLength; i++) {
        if (str[i] == ' ') {
            int spacesInsert = toInsert;
            if (remain) {
                spacesInsert++;
                remain--;
            }
            insertSpaces(str, strLength, i, spacesInsert);
            i += spacesInsert;
        }
    }
    
    printf("Result:\n");
    printf("%s", str);
    
    return 0;
}