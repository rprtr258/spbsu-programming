#include <stdio.h>

bool doesFileExist(const char *filename) {
    FILE *file = fopen(filename, "r");
    if (file == NULL)
        return false;
    fclose(file);
    return true;
}

bool printComments(const char *filename) {
    bool result = false;
    FILE *file = fopen(filename, "r");
    while (!feof(file)) {
        char symbol = '\0';
        fscanf(file, "%c", &symbol);
        if (feof(file))
            break;
        if (symbol == ';') {
            while (symbol != '\n') {
                printf("%c", symbol);
                fscanf(file, "%c", &symbol);
            }
            printf("\n");
            result = true;
        }
    }
    fclose(file);
    
    return result;
}

int main() {
    printf("Commentary printer. Put your program in \"program.txt\"\n");
    if (!doesFileExist("program.txt")) {
        printf("\"program.txt\" not found\n");
        return 0;
    }
    
    printf("Found comments:\n");
    bool found = printComments("program.txt");
    if (!found)
        printf("No comments found\n");
    return 0;
}

