#pragma once

struct String {
    char *data = nullptr;
    int size = 0;
};

String* createString();
String* createString(const char *string);

bool areEqual(String *str1, String *str2);

String* concate(String *str1, String *str2);
void addChar(String *&str, char const symbol);

String* cloneString(String *string);
String* subString(String *string, const int i, const int len);

int lengthString(String *string);
bool isEmptyString(String *string);

char* rawString(String *string);

void deleteString(String *string);
