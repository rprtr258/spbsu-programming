#include <stdio.h>
#include <string.h>
#include "filesencode.h"
#include "huffman.h"

char* readFile(const char *filename) {
    FILE *file = fopen(filename, "r");
    char buffer[10000];
    char symbol = '\0';
    int i = 0;
    while (!feof(file)) {
        fscanf(file, "%c", &symbol);
        buffer[i] = symbol;
        i++;
    }
    buffer[i] = '\0';
    char *result = new char[i + 1];
    strcpy(result, buffer);
    
    fclose(file);
    return result;
}

int encodeFile(const char *fileInput, const char *fileOutput, const char *fileInfo) {
    char *str = readFile(fileInput);
    
    HuffmanTree *tree = createTree(str);
    FILE *outFile = fopen(fileOutput, "w");
    char *codeString = encode(tree, str);
    
    FILE *infoFile = fopen(fileInfo, "w");
    saveInfo(tree, infoFile, strlen(str));
    
    saveTree(tree, outFile);
    fprintf(outFile, "%s", codeString);
    
    fclose(outFile);
    fclose(infoFile);
    delete[] codeString;
    delete[] str;
    erase(tree);
    delete tree;
    return 0;
}