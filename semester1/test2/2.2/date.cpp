#include <stdlib.h>
#include <string>
#include "date.h"

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

