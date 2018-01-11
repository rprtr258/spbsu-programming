#include <stdio.h>
#include <time.h>
#include <stdlib.h>
#include "cyclelist.h"

int main() {
    srand(time(nullptr));
    printf("CycleList linked list simulator\n");
    CycleList *list = nullptr;
    bool isRunning = true;
    while (isRunning) {
        printf("Write command:\n");
        int cmd = -1;
        scanf(" %d", &cmd);
        switch (cmd) {
            case 0: {
                isRunning = false;
                break;
            }
            case 1: {
                int size = -1;
                int lowestValue = 0;
                int highestValue = 0;
                printf("Write size: ");
                scanf("%d", &size);
                while (size <= 0) {
                    printf("Incorrect size\n");
                    break;
                }
                printf("Write lowest and highest possible values: ");
                scanf("%d %d", &lowestValue, &highestValue);
                if (lowestValue > highestValue) {
                    printf("Incorrect bounds\n");
                    break;
                }
                listDelete(list);
                list = listCreate(size, lowestValue, highestValue);
                break;
            }
            case 2: {
                listReverse(list);
                break;
            }
            case 3: {
                listPrint(list);
                break;
            }
            default: {
                printf("Incorrect command\n");
                break;
            }
        }
    }
    listDelete(list);
    return 0;
}
