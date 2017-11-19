#include <stdio.h>
#include <string.h>
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

int main() {
    printf("Don\'t open \"dontopen.txt\", here is secret info that will be encoded\n");
    char *str = readFile("dontopen.txt");
    
    HuffmanTree *tree = createTree(str);
    FILE *outFile = fopen("encoded.txt", "w");
    char *codeString = encode(tree, str);
    
    FILE *infoFile = fopen("codeInfo.txt", "w");
    saveInfo(tree, infoFile, strlen(codeString), strlen(str));
    
    saveTree(tree, outFile);
    fprintf(outFile, "%s", codeString);
    
    printf("Done!");
    
    fclose(outFile);
    fclose(infoFile);
    delete[] codeString;
    delete[] str;
    erase(tree);
    delete tree;
    return 0;
}