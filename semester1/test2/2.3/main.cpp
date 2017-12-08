#include <stdio.h>
#include "list/list.h"

int main() {
    printf("Brand new sort program!\n");
    
    int arraySize = 0;
    printf("Write size of array: ");
    scanf("%d", &arraySize);
    if (arraySize < 0) {
        printf("Wrong array size\n");
        return 0;
    }
    
    LinkedList *list = createList();
    printf("Write array:\n");
    for (int i = 0; i < arraySize; i++) {
        int value = -1;
        scanf("%d", &value);
        int pos = lowerBound(list, value);
        insertAtIndex(list, value, pos);
        
        //printList(list);
    }
    
    printList(list);
    
    deleteList(list);
    return 0;
}
