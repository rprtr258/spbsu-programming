#include <stdio.h>
#include "hashtable.h"

unsigned int getHash(String *string, int const mod) {
    if (isEmptyString(string))
        return 0;
    
    int const q = 29;
    unsigned int result = 0;
    for (int i = 0; i < string->size; i++) {
        result = (result * q) % mod;
        result = (result + string->data[i]) % mod;
    }
    
    return result;
}

HashTable* createHashTable(int const size) {
    if (size <= 1)
        return nullptr;
    
    HashTable *result = new HashTable();
    result->size = size;
    result->data = new LinkedList*[size];
    for (int i = 0; i < size; i++)
        result->data[i] = createList();
    return result;
}

void deleteHashTable(HashTable *&hashTable) {
    if (hashTable == nullptr)
        return;
    for (int i = 0; i < hashTable->size; i++)
        deleteList(hashTable->data[i]);
    delete[] hashTable->data;
    delete hashTable;
    hashTable = nullptr;
}

void resize(HashTable *&hashTable, int const newSize) {
    if (newSize <= 1)
        return;
    
    HashTable *newHashTable = createHashTable(newSize);
    newHashTable->elements = hashTable->elements;
    newHashTable->size = newSize;
    for (int i = 0; i < hashTable->size; i++) {
        for (int j = 0; j < hashTable->data[i]->size; j++) {
            String *curValue = getValueById(hashTable->data[i], j);
            int curCount = getCountById(hashTable->data[i], j);
            int hash = getHash(curValue, newSize);
            
            insert(newHashTable->data[hash], curValue, curCount);
            
            deleteString(curValue);
        }
    }
    deleteHashTable(hashTable);
    hashTable = newHashTable;
}

void insertString(HashTable *&hashTable, String *string) {
    if (hashTable == nullptr)
        return;
    
    unsigned int hash = getHash(string, hashTable->size);
    if (!doesContain(hashTable, string))
        hashTable->elements++;
    insert(hashTable->data[hash], string);
    
    if (getLoadFactor(hashTable) > 2)
        resize(hashTable, hashTable->size * 2 + 1);
}

bool doesContain(HashTable *hashTable, String *string) {
    if (hashTable == nullptr)
        return false;
    
    unsigned int hash = getHash(string, hashTable->size);
    return hasElement(hashTable->data[hash], string);
}

double getLoadFactor(HashTable *hashTable) {
    if (hashTable == nullptr)
        return -1;
    
    return ((double)hashTable->elements) / hashTable->size;
}

double getAverageChainLength(HashTable *hashTable) {
    if (hashTable == nullptr)
        return -1;
    
    int sizeSum = 0;
    for (int i = 0; i < hashTable->size; i++)
        sizeSum += hashTable->data[i]->size;
    
    return ((double)sizeSum) / hashTable->size;
}

int getMaxChainLength(HashTable *hashTable) {
    if (hashTable == nullptr)
        return -1;
    
    int countMax = 0;
    for (int i = 0; i < hashTable->size; i++) {
        int temp = hashTable->data[i]->size;
        if (temp > countMax)
            countMax = temp;
    }
    return countMax;
}

int getOverallCount(HashTable *hashTable) {
    if (hashTable == nullptr)
        return -1;
    
    int countSum = 0;
    for (int i = 0; i < hashTable->size; i++)
        for (int j = 0; j < hashTable->data[i]->size; j++)
            countSum += getCountById(hashTable->data[i], j);
    
    return countSum;
}

int getEmptyCells(HashTable *hashTable) {
    if (hashTable == nullptr)
        return -1;
    
    int result = 0;
    for (int i = 0; i < hashTable->size; i++)
        if (hashTable->data[i]->size == 0)
            result++;
    
    return result;
}

void printHashTable(HashTable *hashTable, bool const showEmpty) {
    if (hashTable == nullptr) {
        printf("Empty hash table\n");
        return;
    }
    
    for (int i = 0; i < hashTable->size; i++) {
        if (hashTable->data[i]->size == 0 && !showEmpty)
            continue;
        
        printf("%d: ", i);
        printList(hashTable->data[i]);
    }
}
