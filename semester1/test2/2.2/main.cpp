#include <stdio.h>
#include <limits.h>
#include <string>
#include "date.h"

int main() {
    FILE *file = fopen("file.txt", "r");
    char c = ' ';
    std::string word = "";
    int minTime = INT_MAX;
    
    while (!feof(file)) {
        fscanf(file, "%c", &c);
        if (feof(file))
            continue;
        if (c != ' ') {
            word += c;
        } else {
            if (isDate(word)) {
                int time = parseDate(word);
                minTime = std::min(minTime, time);
            }
            word = "";
        }
    }
    if (isDate(word)) {
        int time = parseDate(word);
        minTime = std::min(minTime, time);
    }
    
    if (minTime == INT_MAX)
        printf("Time not found in \"file.txt\"\n");
    else
        printf("Min time in \"file.txt\": %s\n", dateToString(minTime).c_str());
        
    fclose(file);
    return 0;
}
