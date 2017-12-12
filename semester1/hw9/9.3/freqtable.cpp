#include <string.h>
#include <stdio.h>
#include <algorithm>
#include "freqtable.h"

int const alphabet = 256;

FrequencyTable::FrequencyTable(const unsigned char *str) {
    int charCount[alphabet];
    for (int i = 0; i < alphabet; i++)
        charCount[i] = 0;
    int strLength = strlen((char*)str);
    for (int i = 0; i < strLength; i++)
        charCount[str[i]]++;
    
    CharOccur table[alphabet];
    for (int i = 0; i < alphabet; i++)
        table[i] = CharOccur((char)i, charCount[i]);
    std::sort(table, table + alphabet, [](const CharOccur &occur1, const CharOccur &occur2) {
        return occur1.second < occur2.second;
    });
    
    int ptr = 0;
    while (table[ptr].second == 0)
        ptr++;
    
    data = new CharOccur[alphabet - ptr];
    for (int i = ptr; i < alphabet; i++)
        data[i - ptr] = table[i];
    
    size = alphabet - ptr;
}

FrequencyTable::FrequencyTable(const char *filename) {
    FILE *file = fopen(filename, "rb");
    
    int charCount[alphabet];
    for (int i = 0; i < alphabet; i++)
        charCount[i] = 0;
    
    CharOccur table[alphabet];
    
    int symbol = '$';
    while (symbol != EOF) {
        symbol = fgetc(file);
        if (symbol == EOF)
            continue;
        charCount[symbol]++;
    }
    
    for (int i = 0; i < alphabet; i++)
        table[i] = CharOccur((char)i, charCount[i]);
    std::sort(table, table + alphabet, [](const CharOccur &occur1, const CharOccur &occur2) {
        return occur1.second < occur2.second;
    });
    
    int ptr = 0;
    while (table[ptr].second == 0)
        ptr++;
    
    data = new CharOccur[alphabet - ptr];
    for (int i = ptr; i < alphabet; i++)
        data[i - ptr] = table[i];
    
    size = alphabet - ptr;
    
    fclose(file);
}

FrequencyTable::~FrequencyTable() {
    delete[] data;
}

CharOccur& FrequencyTable::operator[](int const &index) {
    return data[index];
}

const CharOccur& FrequencyTable::operator[](int const &index) const {
    return data[index];
}