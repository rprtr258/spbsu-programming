#include <stdio.h>
#include <string.h>
#include "../string/string.h"

bool testCreationDeletion() {
    bool result = true;
    
    String *temp = stringCreate();
    stringDelete(temp);
    
    String *temp2 = stringCreate("Omaewa");
    stringDelete(temp2);
    
    return result;
}

bool testCreationEraseDeletion() {
    bool result = true;
    
    String *temp = stringCreate();
    stringErase(temp);
    stringDelete(temp);
    
    String *temp2 = stringCreate("Omaewa");
    stringErase(temp2);
    stringDelete(temp2);
    
    return result;
}

bool testGetRaw() {
    bool result = true;
    
    char str[] = "q9+";
    String *temp = stringCreate(str);
    char *raw = stringGetRaw(temp);
    for (int unsigned i = 0; i < strlen(str); i++)
        result &= (str[i] == raw[i]);
    
    stringDelete(temp);
    delete[] raw;
    
    return result;
}

bool testSubstring() {
    bool result = true;
    
    char str[] = "Get the flag!";
    char sub[] = "flag";
    String *string = stringCreate(str);
    String *substr = stringGetSubstring(string, 8, 4);
    char *raw = stringGetRaw(substr);
    for (int unsigned i = 0; i < strlen(sub); i++)
        result &= (sub[i] == raw[i]);
    
    stringDelete(string);
    stringDelete(substr);
    delete[] raw;
    
    return result;
}

bool testConcate() {
    bool result = true;
    
    char str1[] = "ab";
    char str2[] = "cd";
    char res[] = "abcd";
    String *string1 = stringCreate(str1);
    String *string2 = stringCreate(str2);
    String *concated = stringConcate(string1, string2);
    char *raw = stringGetRaw(concated);
    for (int unsigned i = 0; i < strlen(res); i++)
        result &= (res[i] == raw[i]);
    
    stringDelete(string1);
    stringDelete(string2);
    stringDelete(concated);
    delete[] raw;
    
    return result;
}

bool testIsLess() {
    bool result = true;
    
    String *string1 = stringCreate("1");
    String *string2 = stringCreate("2");
    result &= stringIsLess(string1, string2);
    
    stringDelete(string1);
    stringDelete(string2);
    
    return result;
}

int main() {
    char passed[] = "\x1b[32mpassed\x1b[0m";
    char failed[] = "\x1b[31mfailed\x1b[0m";
    
    printf("CreationDeletion test %s!\n", testCreationDeletion() ? passed : failed);
    printf("CreationEraseDeletion test %s!\n", testCreationEraseDeletion() ? passed : failed);
    printf("GetRaw test %s!\n", testGetRaw() ? passed : failed);
    printf("Substring test %s!\n", testSubstring() ? passed : failed);
    printf("Concate test %s!\n", testConcate() ? passed : failed);
    printf("IsLess test %s!\n", testIsLess() ? passed : failed);
    
    return 0;
}
