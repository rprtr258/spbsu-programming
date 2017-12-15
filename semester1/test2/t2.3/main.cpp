#include <stdio.h>

bool doesFileExist(const char *filename) {
    FILE *file = fopen(filename, "r");
    if (file == NULL)
        return false;
    fclose(file);
    return true;
}

void printComments(const char *filename) {
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
        }
    }
    fclose(file);
}

int main() {
    printf("Commantary printer. Put your program in \"program.txt\"\n");
    if (!doesFileExist("program.txt")) {
        printf("\"program.txt\" not found\n");
        return 0;
    }
    
    printf("Found comments:\n");
    printComments("program.txt");
    return 0;
}

