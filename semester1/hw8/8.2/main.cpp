#include <stdio.h>
#include "../../utils/string.h"

void clearString(String *&string) {
	if (string != nullptr) {
		deleteString(string);
		delete string;
		string = nullptr;
	}
}

int main() {
    String* epic = createString("Hello, ");
    String* win = createString("World!");
    String* wut = concate(epic, win);
    char *tmp = rawString(wut);
    
    printf("%s\n", tmp);
    
    String *substr1 = subString(wut, 3, 2);
    String *substr2 = subString(substr1, 0, 1);
    String *substr = concate(substr1, substr2);

    printf("%s\n", substr->data);

    clearString(epic);
    clearString(win);
    clearString(wut);
    clearString(substr1);
    clearString(substr2);
    clearString(substr);
    delete[] tmp;
    return 0;
}

