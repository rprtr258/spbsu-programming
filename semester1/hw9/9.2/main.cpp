#include <stdio.h>
#include <string.h>
#include "filesencode.h"

bool doesFileExist(const char *filename) {
    FILE *file = fopen(filename, "r");
    if (file == NULL)
        return false;
    fclose(file);
    return true;
}

int main() {
    printf("Don\'t open \"dontopen.txt\", here is secret info that will be encoded\n");
    printf("Sorry, but this version doesn\'t work correct with one-letter texts\n");
    if (!doesFileExist("dontopen.txt")) {
        printf("\"dontopen.txt\" not found\n");
        return 0;
    }
    
    encodeFile("dontopen.txt", "encoded.txt", "codeInfo.txt");
    
    printf("Done!");
    return 0;
}