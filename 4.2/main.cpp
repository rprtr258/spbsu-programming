#include <stdio.h>

int main() {
    printf("First letters of each word in \"file.txt\"\n");
    
    FILE *myfile = fopen("file.txt", "r");
    char s[1000];
    
    while (!feof(myfile)) {
        fscanf(myfile, "%s", s);
        printf("%c ", s[0]);
    }
    
    fclose(myfile);
    delete myfile;
    return 0;
}
