#include <stdio.h>
#include "../string/string.h"
#include "fileops.h"

void putInt(int *&array, int const value, int unsigned &arrayLength) {
    int *newArray = new int[arrayLength + 1];
    for (int unsigned i = 0; i < arrayLength; i++)
        newArray[i] = array[i];
    newArray[arrayLength] = value;
    if (array != nullptr)
        delete[] array;
    
    array = newArray;
    arrayLength++;
}

int* fileReadIntArray(FILE *file, int unsigned &resultLength) {
    int *result = nullptr;
    resultLength = 0;
    
    int value = -1;
    fscanf(file, "%d", &value);
    while (!feof(file)) {
        putInt(result, value, resultLength);
        fscanf(file, "%d", &value);
    }
    putInt(result, value, resultLength);
    
    return result;
}

int* fileReadIntArray(char const *filename, int unsigned &resultLength) {
    FILE *file = fopen(filename, "r");
    int *result = fileReadIntArray(file, resultLength);
    fclose(file);
    return result;
}

void fileWriteIntArray(FILE *file, int *array, int unsigned const arrayLength) {
    for (int unsigned i = 0; i < arrayLength; i++) {
        fprintf(file, "%d", array[i]);
	if (i + 1 < arrayLength)
		fprintf(file, " ");
    }
}

void fileWriteIntArray(char const *filename, int *array, int unsigned const arrayLength) {
    FILE *file = fopen(filename, "w");
    fileWriteIntArray(file, array, arrayLength);
    fclose(file);
}

void putString(String **&array, char const *value, int unsigned &arrayLength) {
    String **newArray = new String*[arrayLength + 1];
    for (int unsigned i = 0; i < arrayLength; i++)
        newArray[i] = array[i];
    newArray[arrayLength] = stringCreate(value);
    if (array != nullptr)
        delete[] array;
    
    array = newArray;
    arrayLength++;
}

String** fileReadWordsArray(FILE *file, int unsigned &resultLength) {
    String **result = nullptr;
    resultLength = 0;
    
    char value[1001];
    fscanf(file, "%1000s", value);
    while (!feof(file)) {
        putString(result, value, resultLength);
        fscanf(file, "%1000s", value);
    }
    putString(result, value, resultLength);
    
    return result;
}

String** fileReadWordsArray(char const *filename, int unsigned &resultLength) {
    FILE *file = fopen(filename, "r");
    String **result = fileReadWordsArray(file, resultLength);
    fclose(file);
    return result;
}

void fileWriteWordsArray(FILE *file, String **array, int unsigned const arrayLength) {
    for (int unsigned i = 0; i < arrayLength; i++) {
        fprintf(file, "%s", array[i]->data);
	if (i + 1 < arrayLength)
		fprintf(file, " ");
    }
}

void fileWriteWordsArray(char const *filename, String **array, int unsigned const arrayLength) {
    FILE *file = fopen(filename, "w");
    fileWriteWordsArray(file, array, arrayLength);
    fclose(file);
}

void removeNewline(char *str) {
    int unsigned i = 0;
    while (str[i] != '\0' && str[i] != '\n')
        i++;
    if (str[i] == '\n')
        str[i] = '\0';
}

String** fileReadStringsArray(FILE *file, int unsigned &resultLength) {
    String **result = nullptr;
    resultLength = 0;
    
    char value[1001];
    fgets(value, 1000, file);
    removeNewline(value);
    while (!feof(file)) {
        putString(result, value, resultLength);
        fgets(value, 1000, file);
        removeNewline(value);
    }
    putString(result, value, resultLength);
    
    return result;
}

String** fileReadStringsArray(char const *filename, int unsigned &resultLength) {
    FILE *file = fopen(filename, "r");
    String **result = fileReadStringsArray(file, resultLength);
    fclose(file);
    return result;
}

void fileWriteStringsArray(FILE *file, String **array, int unsigned const arrayLength) {
    for (int unsigned i = 0; i < arrayLength; i++) {
        fprintf(file, "%s", array[i]->data);
	if (i + 1 < arrayLength)
		fprintf(file, "\n");
    }
}

void fileWriteStringsArray(char const *filename, String **array, int unsigned const arrayLength) {
    FILE *file = fopen(filename, "w");
    fileWriteStringsArray(file, array, arrayLength);
    fclose(file);
}

