#include <stdio.h>
#include <string.h>

bool isPalindrome(char *string, const int &length) {
    int i = 0;
    int j = length - 1;
    while (i < j) {
        if (string[i] != string[j])
            return false;
        i++;
        j--;
    }
    return true;
}

int main() {
    char string[100];
    printf("Write string you want to check for palindrome:\n");
    scanf("%s", string);
    
    int stringLength = strlen(string);
    bool palindromic = isPalindrome(string, stringLength);
    
    if (palindromic)
        printf("%s is palindromic", string);
    else
        printf("%s is not palindromic", string);
    return 0;
}