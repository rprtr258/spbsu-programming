#pragma once
#include "../list/list.h"
#include "../string/string.h"

struct HashTable {
    LinkedList **data = nullptr;
    int size = 0;
    int elements = 0;
};

HashTable* createHashTable(int const size);
bool doesContain(HashTable *hashTable, String *string);
void deleteHashTable(HashTable *&hashTable);

void resize(HashTable *&hashTable, int const newSize);

void insertString(HashTable *&hashTable, String *string);

bool doesContain(HashTable *hashTable, String *string);
double getLoadFactor(HashTable *hashTable);
double getAverageChainLength(HashTable *hashTable);
int getMaxChainLength(HashTable *hashTable);
int getOverallCount(HashTable *hashTable);
int getEmptyCells(HashTable *hashTable);

void printHashTable(HashTable *hashTable, bool const showEmpty = false);
