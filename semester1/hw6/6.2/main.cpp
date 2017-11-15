#include <stdio.h>
#include <stdlib.h>
#include "cyclelist.h"

int main() {
    int peopleCount = 0;
    int killOrder = 0;
    printf("Write number of people(n) and killing shift(m)\n(negative n for debug):\n");
    scanf("%d %d", &peopleCount, &killOrder);

    CycleList *clist = listCreate(abs(peopleCount));
    bool debugMode = (peopleCount < 0);
    while (listSize(clist) != 1) {
        listMoveCurrent(clist, killOrder - 1);
        if (debugMode) {
            printList(clist);
            printf("killed %d\n", listGetCurrent(clist));
        }
        listRemoveCurrent(clist);
    }
    printf("You should stand at position %d to survive!", listGetCurrent(clist));

    listClear(clist);
    delete clist;
    return 0;
}