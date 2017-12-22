#include <stdio.h>
#include <string.h>

int main() {
    char s[101];
    printf("Write bracket sequence:\n");
    scanf("%100s", s);
    
    int opened = 0;
    int length = strlen(s);
    bool correct = true;
    for (int i = 0; i < length; i++) {
        if (s[i] == '(') {
            opened++;
        } else {// s[i] == ')'
            if (opened == 0)
                correct = false;
            else
                opened--;
        }
    }
    
    if (correct && opened == 0)
        printf("This is correct bracket sequence");
    else
        printf("This is incorrect bracket sequence");
    return 0;
}