#pragma once
#include "../stringList/stringList.h"
#include "../string/string.h"

struct HashTable {
    StrongList **data = nullptr;
    int unsigned size = 0;
    int unsigned elements = 0;
};

HashTable* hashTableCreate(int unsigned const size = 2);
void hashTableDelete(HashTable *&hashTable);

void hashTableResize(HashTable *&hashTable, int unsigned const newSize);

void hashTableInsert(HashTable *&hashTable, String *string);
void hashTableInsert(HashTable *&hashTable, const char *string);

bool hashTableContains(HashTable *hashTable, String *string);
bool hashTableContains(HashTable *hashTable, char const *string);
double hashTableGetLoadFactor(HashTable *hashTable);
double hashTableGetAverageChain(HashTable *hashTable);
int unsigned hashTableGetMaxChain(HashTable *hashTable);
int unsigned hashTableGetEmptyCellsCount(HashTable *hashTable);

void hashTablePrint(HashTable *hashTable, bool const showEmpty = false);

