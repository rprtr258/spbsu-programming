#pragma once
#include <utility>

typedef std::pair<char, int> CharOccur;

struct FrequencyTable {
    CharOccur *data = nullptr;
    int size = 0;
    
};

FrequencyTable* createFreqTable(const char *str);

void deleteFreqTable(FrequencyTable *&ftable);
