#include <stdio.h>
#include <string.h>
#include "decoder.h"

// TODO: fix one-symbol text
// TODO: save tree compactly with bits delimiters
// TODO: turn into utility

int main() {    
    encode("dontopen.txt", "encoded.txt");
    decode("encoded.txt", "decoded.txt");
    
    return 0;
}