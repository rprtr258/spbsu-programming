#pragma once

struct Entry {
    char telephone[21];
    char name[21];
};

struct PhoneBook {
    Entry *data = nullptr;
    int capacity = 0;
    int size = 0;
};

void printBook(PhoneBook *book);

void readEntries(PhoneBook *book);
void saveEntries(PhoneBook *book);

void findByPhone(PhoneBook *book, char *phone);
void findByName(PhoneBook *book, char *name);

void addEntry(PhoneBook *book, char name[21], char phone[21]);

void erase(PhoneBook *book);