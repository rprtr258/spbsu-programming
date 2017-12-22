#include <stdio.h>
#include "string/string.h"
#include "hashtable/hashtable.h"

int main() {
    printf("Brand new technology to count different words in file.txt\n");
    FILE *file = fopen("file.txt", "r");
    if (file == NULL) {
        printf("Sorry, file \"file.txt\" was not found\n");
        return 0;
    }
    HashTable *hashTable = createHashTable(2);
    
    while (!feof(file)) {
        String *word = createString();
        char symbol = '\0';
        fscanf(file, "%c", &symbol);
        while (symbol != ' ' && symbol != '\n' && !feof(file)) {
            String *newWord = putChar(word, symbol);
            deleteString(word);
            word = newWord;
            
            fscanf(file, "%c", &symbol);
        }
        if (isEmptyString(word)) {
            deleteString(word);
            continue;
        }
        
        insertString(hashTable, word);
        deleteString(word);
    }
    
    printf("Hash table info:\n");
    printHashTable(hashTable);
    printf("Load factor: %.10f\n", getLoadFactor(hashTable));
    printf("Average chain: %.10f\n", getAverageChainLength(hashTable));
    printf("Max chain: %d\n", getMaxChainLength(hashTable));
    printf("Overall count: %d\n", getOverallCount(hashTable));
    printf("Empty cells: %d\n", getEmptyCells(hashTable));
    
    fclose(file);
    deleteHashTable(hashTable);
    return 0;
}
