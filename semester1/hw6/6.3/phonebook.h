#pragma once

struct Entry {
    char telephone[20];
    char name[20];
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

void addEntry(PhoneBook *book, char name[20], char phone[20]);

void erase(PhoneBook *book);