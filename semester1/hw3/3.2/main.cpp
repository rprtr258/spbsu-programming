#include <stdio.h>
#include <string.h>
#include <algorithm>

int main() {
    printf("Program that tests if you can change order of chars in one string to make other.\n");
    
    char str1[100] = "\0";
    int str1Length = 0;
    char str2[100] = "\0";
    int str2Length = 0;
    
    printf("Write first string:\n");
    scanf("%s", str1);
    str1Length = strlen(str1);
    printf("Write second string:\n");
    scanf("%s", str2);
    str2Length = strlen(str2);
    
    std::sort(str1, str1 + str1Length);
    std::sort(str2, str2 + str2Length);

    if (strcmp(str1, str2) == 0)
        printf("You can make second string from first");
    else
        printf("These strings can't be made from each other");
    
    return 0;
}