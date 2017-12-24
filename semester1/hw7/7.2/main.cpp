#include <stdio.h>
#include <string.h>
#include "tree.h"

int main() {
    printf("Binary search tree emulation\n");
    printf("Type \"0\" for help\n");
    BinarySearchTree *tree = new BinarySearchTree();
    bool isRunning = true;
    while (isRunning) {
        printf("> ");
        int cmd = -1;
        scanf("%d", &cmd);
        switch (cmd) {
            case 0: {
                printf("List of commands:\n");
                printf("\"1\" - quit programm\n");
                printf("\"2\" - add element in set\n");
                printf("\"3\" - remove element from set\n");
                printf("\"4\" - check for element existance in set\n");
                printf("\"5\" - print tree in debug view\n");
                printf("\"6\" - print elements in increasing order\n");
                printf("\"7\" - print elements in decreasing order\n");
                break;
            }
            case 1: {
                isRunning = false;
                break;
            }
            case 2: {
                int value = 0;
                printf("Write value to add: ");
                scanf("%d", &value);
                treeAdd(tree, value);
                printf("Value %d added!\n", value);
                break;
            }
            case 3: {
                int value = 0;
                printf("Write value to remove: ");
                scanf("%d", &value);
                treeRemove(tree, value);
                break;
            }
            case 4: {
                int value = 0;
                printf("Write value to search: ");
                scanf("%d", &value);
                bool isInside = treeContains(tree, value);
                printf("%d %s in set\n", value, isInside ? "is" : "isn\'t");
                break;
            }
            case 5: {
                treePrintDebug(tree);
                break;
            }
            case 6: {
                treePrintIncreasing(tree);
                break;
            }
            case 7: {
                treePrintDecreasing(tree);
                break;
            }
            default: {
                printf("Incorrect command\n");
                break;
            }
        }
    }
    
    treeErase(tree);
    delete tree;
    return 0;
}