#include <string.h>
#include <utility>
#include "EntryList.h"

Entry* createEntry(const char *string, int unsigned const index) {
    Entry *result = new Entry();
    result->string = new char[strlen(string) + 1];
    strcpy(result->string, string);
    result->index = index;
    return result;
}

Entry* copyEntry(const Entry *entry) {
    if (entry == nullptr)
        return nullptr;
    return createEntry(entry->string, entry->index);
}

void putEntry(EntryList *&entryList, const char *string) {
    if (entryList == nullptr)
        return;
    
    EntryList *newEntryList = new EntryList();
    newEntryList->size = entryList->size + 1;
    newEntryList->data = new Entry*[entryList->size + 1];
    for (int i = 0; i < entryList->size; i++)
        newEntryList->data[i] = copyEntry(entryList->data[i]);
    
    newEntryList->data[entryList->size] = createEntry(string, entryList->size);
    
    deleteList(entryList);
    entryList = newEntryList;
}

void putEntry(EntryList *&entryList, Entry *entry) {
    if (entryList == nullptr)
        return;
    
    EntryList *newEntryList = new EntryList();
    newEntryList->size = entryList->size + 1;
    newEntryList->data = new Entry*[entryList->size + 1];
    for (int i = 0; i < entryList->size; i++)
        newEntryList->data[i] = copyEntry(entryList->data[i]);
    
    newEntryList->data[entryList->size] = copyEntry(entry);
    
    deleteList(entryList);
    entryList = newEntryList;
}

void deleteList(EntryList *&entryList) {
    if (entryList == nullptr)
        return;
    
    for (int i = 0; i < entryList->size; i++) {
        delete[] entryList->data[i]->string;
        delete entryList->data[i];
    }
    if (entryList->data != nullptr)
        delete[] entryList->data;
        
    delete entryList;
    entryList = nullptr;
}

void sortByStrings(EntryList *entryList) {
    if (entryList == nullptr)
        return;
    
    for (int i = 0; i < entryList->size; i++) {
        for (int j = 0; j < i; j++) {
            if (strcmp(entryList->data[i]->string, entryList->data[j]->string) < 0) {
                std::swap(entryList->data[i], entryList->data[j]);
            }
        }
    }
    // stabilizing indexes
    for (int i = 0; i < entryList->size; i++) {
        for (int j = 0; j < i; j++) {
            if (strcmp(entryList->data[i]->string, entryList->data[j]->string) == 0 &&
                       entryList->data[i]->index < entryList->data[j]->index) {
                std::swap(entryList->data[i], entryList->data[j]);
            }
        }
    }
}

void sortByIndexes(EntryList *entryList) {
    if (entryList == nullptr)
        return;
    for (int i = 0; i < entryList->size; i++) {
        for (int j = 0; j < i; j++) {
            if (entryList->data[i]->index < entryList->data[j]->index) {
                std::swap(entryList->data[i], entryList->data[j]);
            }
        }
    }
}

EntryList* getUnique(EntryList *entryList) {
    if (entryList == nullptr || entryList->size == 0)
        return nullptr;
    
    EntryList* result = new EntryList();
    putEntry(result, entryList->data[0]);
    for (int i = 1; i < entryList->size; i++) {
        if (strcmp(result->data[result->size - 1]->string, entryList->data[i]->string) != 0) {
            putEntry(result, entryList->data[i]);
        }
    }
    
    return result;
}
