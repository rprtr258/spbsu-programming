#include <stdio.h>

int const alphabet = 256;

bool doesFileExist(const char *filename) {
     FILE *temp = fopen(filename, "r");
     if (temp == NULL)
         return false;
     fclose(temp);
     return true;
 }

int main() {
    printf("First occurrences of each letter in each word in \"file.txt\"\n");
    if (!doesFileExist("file.txt")) {
        printf("\"file.txt\" not found\n");
        return 0;
    }
    FILE *myfile = fopen("file.txt", "r");
    char s[1001];
    
    while (!feof(myfile)) {
        fscanf(myfile, "%1000s", s);
        bool were[alphabet];
        for (int i = 0; i < alphabet; i++)
            were[i] = false;
        for (int i = 0; s[i] != '\0'; i++)
            if (!were[s[i]]) {
                printf("%c", s[i]);
                were[s[i]] = true;
            }
        printf(" ");
    }
    
    fclose(myfile);
    return 0;
}
