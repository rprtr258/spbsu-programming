#include <stdio.h>
#include <string.h>
#include "filesencode.h"

int main() {
    printf("Don\'t open \"dontopen.txt\", here is secret info that will be encoded\n");
    printf("Sorry, but this version doesn\'t work correct with one-letter texts\n");
    
    encodeFile("dontopen.txt", "encoded.txt", "codeInfo.txt");
    
    printf("Done!");
    return 0;
}