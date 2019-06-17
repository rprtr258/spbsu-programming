#pragma once
#include "list.h"

struct String {
    char *data = nullptr;
    int size = 0;
};

String* createString();
String* createString(const char *string);
String* subString(String *string, const int i, const int len);
String* concate(String *str1, String *str2);
void addChar(String *&str, char const symbol);
LinkedList* findOccurences(String* text, String* pattern);
char* rawString(String *string);
String* cloneString(String *string);
void eraseString(String *string);
void deleteString(String *&string);
int lengthString(String *string);
bool isEmptyString(String *string);
bool areEqual(String *str1, String *str2);
String* cloneString(String *string);
