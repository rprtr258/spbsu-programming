#pragma once

struct Entry {
    char *string = nullptr;
    int index = -1;
};

struct EntryList {
    Entry **data = nullptr;
    int unsigned size = 0;
};

void sortByStrings(EntryList *entryList);
void sortByIndexes(EntryList *entryList);

EntryList* getUnique(EntryList *entryList);

void putEntry(EntryList *&entryList, const char *string);
void deleteList(EntryList *&entryList);
