#include <stdio.h>
#include <string.h>

bool isBlankChar(const char symbol) {
    return (symbol == ' ' || symbol == '\r' || symbol == '\n' || symbol == '\t');
}

int main() {
    printf("Counting how many is there non-blank lines in \"file.txt\"\n");
    FILE *myFile = fopen("file.txt", "r");
    
    char sym;
    int result = 0;
    while (!feof(myFile)) {
        fscanf(myFile, "%c", &sym);
        if (!isBlankChar(sym)) {
            result++;
            while (!feof(myFile) && sym != '\n')
                fscanf(myFile, "%c", &sym);
        }
    }
    
    printf("There\'s %d non-blank lines", result);
    
    fclose(myFile);
    delete myFile;
    return 0;
}