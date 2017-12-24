#include <stdio.h>
#include <string.h>
#include "phonebook.h"

int main() {
    PhoneBook *book = new PhoneBook();
    readEntries(book);
    printf("Program to work with phone book. Type \"help\" for help, \"exit\" to exit.\n");
    while (true) {
        char cmd[10] = "";
        printf("> ");
        scanf("%9s", cmd);
        if (strcmp(cmd, "exit") == 0) {
            saveEntries(book);
            break;
        } else if (strcmp(cmd, "help") == 0) {
            printf("List of commands(write commands without quotes):\n");
            printf("\"print\" - to print all contacts\n");
            printf("\"add\" - to add contact\n");
            printf("\"findname\" - to find contacts with given name\n");
            printf("\"findphone\" - to find contacts with given phone\n");
            printf("\"save\" - to save database\n");
            printf("\"exit\" - to quit programm\n");
        } else if (strcmp(cmd, "print") == 0) {
            printBook(book);
        } else if (strcmp(cmd, "add") == 0) {
            char name[21];
            printf("Write name of person: ");
            scanf("%20s", name);
            char phone[21];
            printf("Write phone of person: ");
            scanf("%20s", phone);
            addEntry(book, name, phone);
        } else if (strcmp(cmd, "findphone") == 0) {
            char phone[21];
            printf("Write phone you want to find:\n");
            scanf(" %20s", phone);
            findByPhone(book, phone);
        } else if (strcmp(cmd, "findname") == 0) {
            char name[21];
            printf("Write name you want to find:\n");
            scanf(" %20s", name);
            findByName(book, name);
        } else if (strcmp(cmd, "save") == 0) {
            saveEntries(book);
        } else if (strcmp(cmd, "") != 0) {
            printf("Wrong command, please retry\n");
        }
    }
    erase(book);
    delete book;
    return 0;
}
