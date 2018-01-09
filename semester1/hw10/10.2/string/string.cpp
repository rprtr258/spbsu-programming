#include <string.h>
#include "string.h"

String* createString() {
    String *newString = new String();
    return newString;
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

void addChar(String *&str, char const symbol) {
    if (str == nullptr)
        str = createString();
    if (str->size == 0) {
        str->data = new char[2];
        str->data[0] = symbol;
        str->data[1] = '\0';
    } else {
        char *newData = new char[str->size + 2];
        strcpy(newData, str->data);
        newData[str->size] = symbol;
        newData[str->size + 1] = '\0';
        
        delete[] str->data;
        str->data = newData;
    }
    str->size++;
}

int lengthString(String *string) {
    if (string != nullptr)
        return string->size;
    return 0;
}

bool isEmptyString(String *string) {
    return (lengthString(string) == 0);
}

LinkedList* findOccurences(String* text, String* pattern) {
    if (isEmptyString(text) || isEmptyString(pattern))
        return nullptr;
    
    int const q = 73;
    int const p = 10007;
    int qDegree = 1;
    int patternHash = 0;
    for (int i = 0; i < pattern->size; i++) {
        patternHash = (q * patternHash + pattern->data[i]) % p;
        qDegree = (qDegree * q) % p;
    }
    
    int tempHash = 0;
    LinkedList *result = createList();
    for (int i = 0; i < text->size - pattern->size + 1; i++) {
        if (i == 0) {
            for (int j = 0; j < pattern->size; j++)
                tempHash = (q * tempHash + text->data[i + j]) % p;
        } else {
            tempHash = (tempHash * q + text->data[i + pattern->size - 1]) % p;
            tempHash = ((tempHash - qDegree * text->data[i - 1]) % p + p) % p;
        }

        if (tempHash == patternHash) {
            bool found = true;
            for (int j = 0; j < pattern->size; j++)
                found &= (text->data[i + j] == pattern->data[j]);
            if (found)
                insertAtEnd(result, i);
        }
    }
    
    return result;
}

bool areEqual(String *str1, String *str2) {
    if (isEmptyString(str1) && isEmptyString(str2))
        return true;
    if (isEmptyString(str1) || isEmptyString(str2))
        return false;
    
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

void eraseString(String *string) {
    if (string == nullptr)
        return;
    if (string->data != nullptr) {
        delete[] string->data;
        string->data = nullptr;
    }
    string->size = 0;
}

void deleteString(String *&string) {
    if (string == nullptr)
        return;
    eraseString(string);
    delete string;
    string = nullptr;
}
