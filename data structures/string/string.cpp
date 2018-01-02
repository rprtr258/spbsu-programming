#include <string.h>
#include <stdio.h>
#include "string.h"

String* stringCreate() {
    return stringCreate("");
}

String* stringCreate(const char *string) {
    if (string == nullptr)
        return nullptr;
    
    String *result = new String();

    result->size = strlen(string);
    result->data = new char[result->size + 1];
    strcpy(result->data, string);

    return result;
}

String* stringCopy(String *string) {
    if (string == nullptr || string->data == nullptr)
        return nullptr;
    
    String *result = stringCreate(string->data);
    return result;
}

void stringErase(String *&string) {
    if (string == nullptr)
        return;
    
    stringDelete(string);
    string = stringCreate();
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
    if (str1 == nullptr || str2 == nullptr)
        return false;
    
    return (strcmp(str1->data, str2->data) == 0);
}

bool stringIsLess(String *str1, String *str2) {
    if (str1 == nullptr || str2 == nullptr)
        return false;
    
    return (strcmp(str1->data, str2->data) < 0);
}

int stringLength(String *string) {
    return (string == nullptr ? -1 : string->size);
}

bool stringIsEmpty(String *string) {
    return (stringLength(string) == 0);
}

String* stringConcate(String *str1, String *str2) {
    if (str1 == nullptr || str2 == nullptr)
        return nullptr;

    char *newData = new char[stringLength(str1) + stringLength(str2) + 1];

    strcpy(newData, str1->data);
    strcpy(newData + stringLength(str1), str2->data);

    String *result = stringCreate(newData);

    delete[] newData;
    return result;
}

String* stringGetSubstring(String *string, int unsigned const i, int unsigned const len) {
    if (string == nullptr)
        return nullptr;
    
    if (len == 0 || i + len >= string->size)
        return nullptr;
    
    char *substr = new char[len + 1];
    memcpy(substr, string->data + i, len);
    substr[len] = '\0';
    
    String *result = stringCreate(substr);
    
    delete[] substr;
    return result;
}

char* stringGetRaw(String *string) {
    if (string == nullptr)
        return nullptr;
    
    char *result = new char[string->size + 1];
    strcpy(result, string->data);
    return result;
}

