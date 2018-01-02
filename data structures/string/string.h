#pragma once

struct String {
    char *data = nullptr;
    int unsigned size = 0;
};

String* stringCreate();
String* stringCreate(const char *string);
String* stringCopy(String *string);
void stringErase(String *&string);
void stringDelete(String *&string);

bool stringAreEqual(String *str1, String *str2);
bool stringIsLess(String *str1, String *str2);

int stringLength(String *string);
bool stringIsEmpty(String *string);

String* stringConcate(String *str1, String *str2);
String* stringGetSubstring(String *string, int unsigned const i, int unsigned const len);

char* stringGetRaw(String *string);

