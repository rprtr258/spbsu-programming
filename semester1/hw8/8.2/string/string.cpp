#include <string.h>
#include "string.h"

String* createString(const char *string) {
    if (string == nullptr)
        return nullptr;
    
    String *newString = new String();

    newString->size = strlen(string);
    newString->data = new char[newString->size + 1];
    strcpy(newString->data, string);

    return newString;
}

String* subString(String *string, const int i, const int len) {
    if (string == nullptr)
        return nullptr;
    
    if (i < 0 || len <= 0 || i + len >= string->size)
        return nullptr;
    
    char *substr = new char[len + 1];
    memcpy(substr, string->data + i, len);
    substr[len] = '\0';
    
    String *result = createString(substr);
    delete[] substr;
    
    return result;
}

String* concate(String *str1, String *str2) {
    if (isEmptyString(str1) && isEmptyString(str2))
        return nullptr;
    if (isEmptyString(str1))
        return cloneString(str2);
    if (isEmptyString(str2))
        return cloneString(str1);

    char *newData = new char[lengthString(str1) + lengthString(str2) + 1];

    strcpy(newData, str1->data);
    strcpy(newData + lengthString(str1), str2->data);

    String *result = createString(newData);

    delete[] newData;
    return result;
}

void deleteString(String *string) {
    if (string == nullptr)
        return;
    if (string->data != nullptr) {
        delete[] string->data;
        string->data = nullptr;
    }
    string->size = 0;
}

int lengthString(String *string) {
    if (string != nullptr)
        return string->size;
    return 0;
}

bool isEmptyString(String *string) {
    return (lengthString(string) == 0);
}

bool areEqual(String *str1, String *str2) {
    if (isEmptyString(str1) && isEmptyString(str2))
        return true;
    
    return (strcmp(str1->data, str2->data) == 0);
}

char* rawString(String *string) {
    if (string == nullptr || string->data == nullptr)
        return nullptr;
    char *newString = new char[string->size + 1];
    strcpy(newString, string->data);
    return newString;
}

String* cloneString(String *string) {
    if (string == nullptr || string->data == nullptr)
        return nullptr;
    String *newString = createString(string->data);
    return newString;
}
