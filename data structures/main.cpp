#include <stdio.h>
#include "list/list.h"

bool testLinkedList() {
    bool result = true;
    
    LinkedList *temp = createList();
    
    printf("Empty list: ");
    printList(temp);
    
    printf("Insert \'1\' at begin: ");
    insertAtBegin(temp, 1);
    printList(temp);
    
    printf("Insert \'3\' at end: ");
    insertAtEnd(temp, 3);
    printList(temp);
    
    int j = findElement(temp, 3);
    printf("Insert 2 at position %d: ", j);
    insertAtIndex(temp, 2, j);
    printList(temp);
    
    printf("Siblings of 1:\n");
    j = findElement(temp, 1);
    printSiblings(temp, j);
    printList(temp);
    
    result &= (peekBegin(temp) == 1);
    result &= (peekIndex(temp, 1) == 2);
    result &= (peekEnd(temp) == 3);
    
    printf("Delete element at index 1: ");
    deleteIndex(temp, 1);
    printList(temp);
    result &= (temp->size == 2);
    
    printf("Delete element at begin: ");
    deleteBegin(temp);
    printList(temp);
    result &= (temp->size == 1);
    
    printf("Delete element at end: ");
    deleteEnd(temp);
    printList(temp);
    result &= (temp->size == 0);
    
    return result;
}

int main() {
    if (!testLinkedList()) {
        printf("\nLinkedList tests failed\n");
        return 0;
    }
    return 0;
}
