#include <stdio.h>
#include <string.h>
#include "decoder.h"

int main() {    
    encode("dontopen.txt", "encoded.txt");
    decode("encoded.txt", "decoded.txt");
    
    return 0;
}