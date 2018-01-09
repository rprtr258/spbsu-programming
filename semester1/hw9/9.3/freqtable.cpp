#include <string.h>
#include <algorithm>
#include "freqtable.h"

int const alphabet = 256;

CharOccur* createSimpleTable(const char *str) {
    int charCount[alphabet];
    for (int i = 0; i < alphabet; i++)
        charCount[i] = 0;
    
    int strLength = strlen(str);
    for (int i = 0; i < strLength; i++)
        charCount[(int)str[i]]++;
    
    CharOccur *table = new CharOccur[alphabet];
    for (int i = 0; i < alphabet; i++)
        table[i] = CharOccur((char)i, charCount[i]);
    std::sort(table, table + alphabet, [](const CharOccur &occur1, const CharOccur &occur2) {
        return occur1.second < occur2.second;
    });
    
    return table;
}

FrequencyTable* createFreqTable(const char *str) {
    FrequencyTable *ftable = new FrequencyTable();
    
    CharOccur *stable = createSimpleTable(str);
    
    int ptr = 0;
    while (stable[ptr].second == 0)
        ptr++;
    
    ftable->data = new CharOccur[alphabet - ptr];
    for (int i = ptr; i < alphabet; i++)
        ftable->data[i - ptr] = stable[i];
    
    ftable->size = alphabet - ptr;
    
    delete[] stable;
    return ftable;
}

void deleteFreqTable(FrequencyTable *&ftable) {
    if (ftable == nullptr)
        return;
    delete[] ftable->data;
    delete ftable;
    ftable = nullptr;
}
