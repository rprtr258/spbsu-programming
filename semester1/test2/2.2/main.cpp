#include <stdio.h>
#include <stdlib.h>
#include <string>

bool isDate(const std::string &str) {
    // dd.mm.yyyy
    // 0123456789
    if (str.length() != 10)
        return false;
    if (str[2] != '.' || str[5] != '.')
        return false;
    for (int i = 0; i < 10; i++)
        if (i != 2 && i != 5 && !isdigit(str[i]))
            return false;
    return true;
}

int parseDate(const std::string &date) {
    const int daysInYear = 365;
    const int daysInMonth = 30;
    
    int day = atoi(date.substr(0, 2).c_str());
    int month = atoi(date.substr(3, 2).c_str());
    int year = atoi(date.substr(6, 4).c_str());
    return day + month * daysInMonth + year * daysInYear;
}

std::string dateToString(int date) {
    const int daysInYear = 365;
    const int daysInMonth = 30;
    
    int year = date / daysInYear;
    date %= daysInYear;
    int month = date / daysInMonth;
    date %= daysInMonth;
    int days = date;
    
    std::string result = "dd.mm.yyyy";
    result[0] = '0' + days / 10;
    result[1] = '0' + days % 10;
    result[3] = '0' + month / 10;
    result[4] = '0' + month % 10;
    for (int i = 9; i > 5; i--) {
        result[i] = year % 10 + '0';
        year /= 10;
    }
    return result;
}

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
    
    if (minTime == INT_MAX) {
        printf("Time not found in \"file.txt\"\n");
    } else {
        printf("Min time in \"file.txt\": %s\n", dateToString(minTime).c_str());
    }
        
    fclose(file);
    return 0;
}
