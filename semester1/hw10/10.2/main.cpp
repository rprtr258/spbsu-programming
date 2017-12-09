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
        return 0;
    }
    //printf("TEXT:\n%s\n", text->data);
    
    String *pattern = createString();
    printf("Write pattern in quotes like \"word\":\n");
    readString(pattern);
    if (lengthString(pattern) == 0) {
        printf("Empty pattern is everywhere in any text\n");
        deleteString(text);
        deleteString(pattern);
        return 0;
    }
    //printf("PATTERN:\n%s\n", pattern->data);
    
    LinkedList *list = findOccurences(text, pattern);
    if (list->size > 0) {
        printf("Found %d occurences at positions:\n", list->size);
        printList(list);
        printf("\n");
    } else {
        printf("Found no occurences\n");
    }
    
    deleteList(list);
    deleteString(text);
    deleteString(pattern);
    return 0;
}

