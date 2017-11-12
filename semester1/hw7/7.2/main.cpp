#include <stdio.h>
#include <string.h>
#include "tree.h"

int main() {
    printf("Binary search tree emulation\n");
    printf("Type \"help\" for help\n");
    BinarySearchTree *tree = new BinarySearchTree();
    
    while (true) {
        printf("> ");
        char cmd[10];
        scanf("%s", cmd);
        if (strcmp(cmd, "help") == 0) {
            printf("List of commands:\n");
            printf("\"exit\" - quit programm\n");
            printf("\"add\" - add element in set\n");
            printf("\"remove\" - remove element from set\n");
            printf("\"contains\" - check for element existance in set\n");
            printf("\"debug\" - print tree in debug view\n");
            printf("\"printi\" - print elements in increasing order\n");
            printf("\"printd\" - print elements in decreasing order\n");
        } else if (strcmp(cmd, "exit") == 0) {
            break;
        } else if (strcmp(cmd, "add") == 0) {
            int value = 0;
            printf("Write value to add: ");
            scanf("%d", &value);
            treeAdd(tree, value);
            printf("Value %d added!\n", value);
        } else if (strcmp(cmd, "remove") == 0) {
            int value = 0;
            printf("Write value to remove: ");
            scanf("%d", &value);
            treeRemove(tree, value);
        } else if (strcmp(cmd, "contains") == 0) {
            int value = 0;
            printf("Write value to search: ");
            scanf("%d", &value);
            bool isInside = treeContains(tree, value);
            printf(isInside ? "%d is in set\n" : "%d isn\'t in set\n", value);
        } else if (strcmp(cmd, "debug") == 0) {
            treePrintDebug(tree);
        } else if (strcmp(cmd, "printi") == 0) {
            treePrintIncreasing(tree);
        } else if (strcmp(cmd, "printd") == 0) {
            treePrintDecreasing(tree);
        }
    }
    
    treeErase(tree);
    delete tree;
    return 0;
}
