#include <string.h>
#include <stdio.h>
#include "string.h"

String* stringCreate() {
    String *newString = new String();
    return newString;
}

String* stringCreate(const char *string) {
    if (string == nullptr)
        return nullptr;
    
    String *newString = new String();

    newString->size = strlen(string);
    newString->data = new char[newString->size + 1];
    strcpy(newString->data, string);

    return newString;
}

void stringDelete(String *&string) {
    if (string == nullptr)
        return;
    if (string->data != nullptr) {
        delete[] string->data;
    }
    delete string;
    string = nullptr;
}

bool stringAreEqual(String *str1, String *str2) {
    if (stringIsEmpty(str1) && stringIsEmpty(str2))
        return true;
    
    return (strcmp(str1->data, str2->data) == 0);
}

bool stringIsEmpty(String *string) {
    return (string == nullptr || lengthString(string) == 0);
}

String* stringConcate(String *str1, String *str2) {
    if (stringIsEmpty(str1) && stringIsEmpty(str2))
        return nullptr;
    if (stringIsEmpty(str1))
        return stringCopy(str2);
    if (stringIsEmpty(str2))
        return stringCopy(str1);

    char *newData = new char[lengthString(str1) + lengthString(str2) + 1];

    strcpy(newData, str1->data);
    strcpy(newData + lengthString(str1), str2->data);

    String *result = stringCreate(newData);

    delete[] newData;
    return result;
}

String* stringCopy(String *string) {
    if (string == nullptr || string->data == nullptr)
        return nullptr;
    String *newString = stringCreate(string->data);
    return newString;
}

String* stringGetSubstring(String *string, const int i, const int len) {
    if (string == nullptr)
        return nullptr;
    
    if (i < 0 || len <= 0 || i + len >= string->size)
        return nullptr;
    
    char *substr = new char[len + 1];
    memcpy(substr, string->data + i, len);
    substr[len] = '\0';
    
    String *result = stringCreate(substr);
    delete[] substr;
    
    return result;
}

int lengthString(String *string) {
    return (string == nullptr ? 0 : string->size);
}

char* rawString(String *string) {
    if (string == nullptr || string->data == nullptr)
        return nullptr;
    char *newString = new char[string->size + 1];
    strcpy(newString, string->data);
    return newString;
}

bool testString(bool const printDebug) {
    String* epic = stringCreate("Hello, ");
    String* win = stringCreate("World!");
    String* wut = stringConcate(epic, win);
    char *tmp = rawString(wut);
    
    String *substr1 = stringGetSubstring(wut, 3, 2);
    String *substr2 = stringGetSubstring(substr1, 0, 1);
    String *substr = stringConcate(substr1, substr2);
    
    if (printDebug) {
        printf("%s\n", tmp);
        printf("%s\n", substr->data);
    }

    stringDelete(epic);
    stringDelete(win);
    stringDelete(wut);
    stringDelete(substr1);
    stringDelete(substr2);
    stringDelete(substr);
    delete[] tmp;
    return true;
}

