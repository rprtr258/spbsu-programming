#pragma once
#include <stdio.h>
#include "../string/string.h"

int* fileReadIntArray(FILE *file, int unsigned &resultLength);
int* fileReadIntArray(char const *filename, int unsigned &resultLength);

void fileWriteIntArray(FILE *file, int *array, int unsigned const arrayLength);
void fileWriteIntArray(char const *filename, int *array, int unsigned const arrayLength);

String** fileReadWordsArray(FILE *file, int unsigned &resultLength);
String** fileReadWordsArray(char const *filename, int unsigned &resultLength);

void fileWriteWordsArray(FILE *file, String **array, int unsigned const arrayLength);
void fileWriteWordsArray(char const *filename, String **array, int unsigned const arrayLength);

String** fileReadStringsArray(FILE *file, int unsigned &resultLength);
String** fileReadStringsArray(char const *filename, int unsigned &resultLength);

void fileWriteStringsArray(FILE *file, String **array, int unsigned const arrayLength);
void fileWriteStringsArray(char const *filename, String **array, int unsigned const arrayLength);

