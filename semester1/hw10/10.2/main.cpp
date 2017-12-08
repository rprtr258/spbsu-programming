#include <stdio.h>
#include "string/string.h"
#include "list/list.h"

void readString(String *&string) {
    char symbol;
    scanf(" %c", &symbol);
    if (symbol != '\"') {
        printf("Error reading string\n");
        return;
    }
    do {
        scanf("%c", &symbol);
        if (symbol != '\"') {
            addChar(string, symbol);
        }
    } while (symbol != '\"');
}

int main() {
    printf("Robin-karp substring finder\n");
    
    String *text = createString();
    printf("Write text in quotes like \"text\":\n");
    readString(text);
    if (lengthString(text) == 0) {
        printf("Empty string does not contain any patterns\n");
        deleteString(text);
        delete text;
        return 0;
    }
    //printf("TEXT:\n%s\n", text->data);
    
    String *pattern = createString();
    printf("Write pattern in quotes like \"word\":\n");
    readString(pattern);
    if (lengthString(pattern) == 0) {
        printf("Empty pattern is everywhere in any text\n");
        deleteString(text);
        delete text;
        deleteString(pattern);
        delete pattern;
        return 0;
    }
    //printf("PATTERN:\n%s\n", pattern->data);
    
    LinkedList *list = findOccurences(text, pattern);
    printList(list);
    
    deleteList();
    deleteString(text);
    delete text;
    deleteString(pattern);
    delete pattern;
    return 0;
}

