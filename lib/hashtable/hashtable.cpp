#include <stdio.h>
#include "hashtable.h"

unsigned int getHash(String *string, int unsigned const mod) {
    int unsigned const q = 29;
    unsigned int result = 0;
    for (int unsigned i = 0; i < string->size; i++) {
        result = (result * q) % mod;
        result = (result + string->data[i]) % mod;
    }
    return result;
}

HashTable* hashTableCreate(int unsigned const size) {
    if (size <= 1)
        return nullptr;
    
    HashTable *result = new HashTable();
    result->size = size;
    result->data = new StringLinkedList*[size];
    for (int unsigned i = 0; i < size; i++)
        result->data[i] = stringListCreate();
    return result;
}

void hashTableDelete(HashTable *&hashTable) {
    if (hashTable == nullptr)
        return;
    
    for (int unsigned i = 0; i < hashTable->size; i++)
        stringListDelete(hashTable->data[i]);
    delete[] hashTable->data;
    delete hashTable;
    hashTable = nullptr;
}

void hashTableResize(HashTable *&hashTable, int unsigned const newSize) {
    if (newSize <= 1)
        return;
    
    HashTable *newHashTable = hashTableCreate(newSize);
    newHashTable->elements = hashTable->elements;
    newHashTable->size = newSize;
    for (int unsigned i = 0; i < hashTable->size; i++) {
        for (int unsigned j = 0; j < hashTable->data[i]->size; j++) {
            String *curValue = stringListPeekIndex(hashTable->data[i], j);
            hashTableInsert(newHashTable, curValue);
            stringDelete(curValue);
        }
    }
    hashTableDelete(hashTable);
    hashTable = newHashTable;
}

void hashTableInsert(HashTable *&hashTable, String *string) {
    if (hashTable == nullptr || string == nullptr)
        return;
    
    unsigned int hash = getHash(string, hashTable->size);
    stringListInsertAtEnd(hashTable->data[hash], string);
    hashTable->elements++;
    
    if (hashTableGetLoadFactor(hashTable) > 2)
        hashTableResize(hashTable, hashTable->size * 2 + 1);
}

void hashTableInsert(HashTable *&hashTable, char const *value) {
    String *string = stringCreate(value);
    hashTableInsert(hashTable, string);
    stringDelete(string);
}

bool hashTableContains(HashTable *hashTable, String *string) {
    if (hashTable == nullptr)
        return false;
    
    unsigned int hash = getHash(string, hashTable->size);
    return stringListContains(hashTable->data[hash], string);
}

bool hashTableContains(HashTable *hashTable, char const *value) {
    String *string = stringCreate(value);
    bool result = hashTableContains(hashTable, string);
    stringDelete(string);
    return result;
}

double hashTableGetLoadFactor(HashTable *hashTable) {
    if (hashTable == nullptr)
        return -1.0;
    
    return ((double)hashTable->elements) / hashTable->size;
}

double hashTableGetAverageChain(HashTable *hashTable) {
    if (hashTable == nullptr)
        return -1.0;
    
    int unsigned sizeSum = 0;
    for (int unsigned i = 0; i < hashTable->size; i++)
        sizeSum += hashTable->data[i]->size;
    
    return ((double)sizeSum) / hashTable->size;
}

int unsigned getMaxChainLength(HashTable *hashTable) {
    if (hashTable == nullptr)
        return 1234;
    
    int unsigned countMax = 0;
    for (int unsigned i = 0; i < hashTable->size; i++) {
        int unsigned temp = hashTable->data[i]->size;
        if (temp > countMax)
            countMax = temp;
    }
    return countMax;
}

int unsigned hashTableGetEmptyCellsCount(HashTable *hashTable) {
    if (hashTable == nullptr)
        return 1234;
    
    int unsigned result = 0;
    for (int unsigned i = 0; i < hashTable->size; i++)
        if (hashTable->data[i]->size == 0)
            result++;
    
    return result;
}

void hashTablePrint(HashTable *hashTable, bool const showEmpty) {
    if (hashTable == nullptr) {
        printf("Nullptr HashTable\n");
        return;
    }
    if (hashTable->elements == 0) {
        printf("Empty hashtable\n");
        return;
    }
    
    for (int unsigned i = 0; i < hashTable->size; i++) {
        if (hashTable->data[i]->size == 0 && !showEmpty)
            continue;
        
        printf("%u: ", i);
        stringListPrint(hashTable->data[i]);
    }
}
