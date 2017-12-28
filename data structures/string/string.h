#pragma once

struct String {
    char *data = nullptr;
    int size = 0;
};

String* stringCreate();
String* createString(const char *string);
void stringDelete(String *&string);

bool stringAreEqual(String *str1, String *str2);
bool stringIsEmpty(String *string);

String* stringConcate(String *str1, String *str2);
String* stringCopy(String *string);
String* stringGetSubstring(String *string, const int i, const int len);

int lengthString(String *string);
char* rawString(String *string);

bool testString(bool const printDebug = false);

