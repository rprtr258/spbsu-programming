#include <stdio.h>

int const alphabet = 256;

int main() {
    printf("First occurrences of each letter in each word in \"file.txt\"\n");
    
    FILE *myfile = fopen("file.txt", "r");
    char s[1000];
    
    while (!feof(myfile)) {
        fscanf(myfile, "%s", s);
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
    delete myfile;
    return 0;
}
