#include <stdio.h>
#include <string.h>
#include <algorithm>
#include "phonebook.h"

void printBook(PhoneBook *book) {
    if (book->size > 0)
        for (int i = 0; i < book->size; i++)
            printf("Name: %s Phone: %s\n", book->data[i].name, book->data[i].telephone);
    else
        printf("Phone book is empty\n");
}

void readEntries(PhoneBook *book) {
    FILE *fileInput = fopen("database", "r");
    if (fileInput == NULL) {
        printf("Database not found\n");
        return;
    }
    
    int entriesNumber = 0;
    fscanf(fileInput, "%d", &entriesNumber);
    
    if (entriesNumber > 0)
        book->data = new Entry[entriesNumber];
    book->size = entriesNumber;
    book->capacity = entriesNumber;
    for (int i = 0; i < entriesNumber; i++) {
        Entry &entry = book->data[i];
        fscanf(fileInput, "%20s %20s", entry.name, entry.telephone);
    }
    
    fclose(fileInput);
}

void saveEntries(PhoneBook *book) {
    FILE *fileOutput = fopen("database", "w");
    
    int entriesNumber = book->size;
    fprintf(fileOutput, "%d\n", entriesNumber);
    for (int i = 0; i < entriesNumber; i++)
        fprintf(fileOutput, "%s %s\n", book->data[i].name, book->data[i].telephone);
    
    fclose(fileOutput);
}

void findByPhone(PhoneBook *book, char *phone) {
    bool found = false;
    for (int i = 0; i < book->size; i++) {
        if (strcmp(phone, book->data[i].telephone) == 0) {
            Entry &foundEntry = book->data[i];
            if (!found)
                printf("Found:\n");
            printf("Name: %s; Phone: %s\n", foundEntry.name, foundEntry.telephone);
            found = true;
        }
    }
    if (!found)
        printf("Haven't found entries with that phone\n");
}

void findByName(PhoneBook *book, char *name) {
    bool found = false;
    for (int i = 0; i < book->size; i++) {
        if (strcmp(name, book->data[i].name) == 0) {
            Entry &foundEntry = book->data[i];
            if (!found)
                printf("Found:\n");
            printf("Name: %s; Phone: %s\n", foundEntry.name, foundEntry.telephone);
            found = true;
        }
    }
    if (!found)
        printf("Haven't found entries with that name\n");
}

void addEntry(PhoneBook *book, char name[20], char phone[20]) {
    if (book->size == book->capacity) {
        int newCapacity = std::max(1, book->capacity * 2);
        Entry *newData = new Entry[newCapacity];
        for (int i = 0; i < book->size; i++) {
            for (int j = 0; j < 20; j++) {
                newData[i].name[j] = book->data[i].name[j];
                newData[i].telephone[j] = book->data[i].telephone[j];
            }
        }
        if (book->data != nullptr)
            delete[] book->data;
        book->data = newData;
        book->capacity = newCapacity;
    }
    
    for (int i = 0; i < 20; i++) {
        book->data[book->size].name[i] = name[i];
        book->data[book->size].telephone[i] = phone[i];
    }
    
    book->size++;
}

void erase(PhoneBook *book) {
    if (book->data != nullptr)
        delete[] book->data;
    book->data = nullptr;
    book->size = 0;
    book->capacity = 0;
}