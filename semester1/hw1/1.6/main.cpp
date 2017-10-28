#include <stdio.h>
#include <string.h>

int main() {
    char text[100];
    printf("Write text without spaces:\n");
    scanf("%s", text);
    
    char pattern[100];
    printf("Write pattern:\n");
    scanf("%s", pattern);
    
    int textLength = strlen(text);
    int patternLength = strlen(pattern);
    int result = 0;
    for (int i = 0; i + patternLength <= textLength; i++) {
        bool found = true;
        for (int j = 0; j < patternLength; j++) {
            if (text[i + j] != pattern[j]) {
                found = false;
                break;
            }
        }
        if (found)
            result++;
    }
    
    printf("Found %d occurences", result);
    return 0;
}