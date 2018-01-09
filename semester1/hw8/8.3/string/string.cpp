#include <string.h>
#include <stdio.h>
#include "string.h"

String* createString() {
    return new String();
}

String* createString(const char *string) {
    if (string == nullptr)
        return nullptr;
    
    String *newString = new String();

    newString->size = strlen(string);
    newString->data = new char[newString->size + 1];
    strcpy(newString->data, string);

    return newString;
}

void printString(String *string) {
    if (!isEmptyString(string))
        printf("%s", string->data);
}

String* subString(String *string, const int i, const int len) {
    if (string == nullptr)
        return nullptr;
    
    if (i < 0 || len <= 0 || i + len >= string->size)
        return nullptr;
    
    char *substr = new char[len + 1];
    memcpy(substr, string->data + i, len * sizeof(char));
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

String* putChar(String *string, char symbol) {
    if (isEmptyString(string)) {
        char tempString[2] = {symbol, '\0'};
        return createString(tempString);
    }
    
    String *result = createString();
    result->size = string->size + 1;
    result->data = new char[result->size + 1];
    memcpy(result->data, string->data, string->size);
    result->data[string->size] = symbol;
    result->data[string->size + 1] = '\0';
    
    return result;
}

void deleteString(String *&string) {
    if (string == nullptr)
        return;
    if (string->data != nullptr)
        delete[] string->data;
    delete string;
    string = nullptr;
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
    
    return createString(string->data);
}
