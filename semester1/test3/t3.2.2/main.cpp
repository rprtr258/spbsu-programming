#include <stdio.h>
#include <string.h>
#include "EntryList.h"

// also used in scanf in main
int const maxStringLength = 1000;

bool doesFileExist(const char *filename) {
    FILE *file = fopen(filename, "r");
    if (file == NULL)
        return false;
    fclose(file);
    return true;
}

int main() {
    printf("Unique strings parser\n");
    printf("Put your strings in \"file.txt\"\n");
    printf("Newline at end of file indicates end of strings\n");
    if (!doesFileExist("file.txt")) {
        printf("\"file.txt\" not found\n");
        return 0;
    }
    
    char string[maxStringLength + 1];
    FILE *file = fopen("file.txt", "r");
    EntryList *entryList = new EntryList();
    
    fscanf(file, "%1000s", string);
//    printf("STRINGS IN FILE:\n");
    while (!feof(file)) {
//        printf("\"%s\"\n", string);
        putEntry(entryList, string);
        
        fscanf(file, "%1000s", string);
    }
 
    sortByStrings(entryList);
    EntryList *uniqueList = getUnique(entryList);
    sortByIndexes(uniqueList);
    
//    printf("READ ENTRIES:\n");
//    for (int i = 0; i < uniqueList->size; i++) {
//        printf("%u: %s\n", uniqueList->data[i]->index, uniqueList->data[i]->string);
//    }
    
    FILE *fileResult = fopen("result.txt", "w");
    for (int i = 0; i < uniqueList->size; i++) {
        fprintf(fileResult, "%s\n", uniqueList->data[i]->string);
    }
    
    fclose(file);
    fclose(fileResult);
    deleteList(entryList);
    deleteList(uniqueList);
    return 0;
}

