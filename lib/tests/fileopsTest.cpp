#include <stdio.h>
#include "../string/string.h"
#include "../fileops/fileops.h"

bool testWriteIntFile() {
    bool result = true;
    
    int array[] = {1, 2, 3};
    int unsigned length = 3;
    FILE *file = fopen("ints.txt", "w");
    fileWriteIntArray(file, array, length);
    fclose(file);
    
    return result;
}

bool testWriteIntName() {
    bool result = true;
    
    int array[] = {1, 2, 3};
    int unsigned length = 3;
    fileWriteIntArray("ints.txt", array, length);
    
    return result;
}

bool testReadIntFile() {
    bool result = true;
    
    int unsigned length = 0;
    FILE *file = fopen("ints.txt", "r");
    int *array = fileReadIntArray(file, length);
    result &= (length == 3);
    result &= (array[0] == 1);
    result &= (array[1] == 2);
    result &= (array[2] == 3);
    fclose(file);
    delete[] array;
    
    return result;
}

bool testReadIntName() {
    bool result = true;
    
    int unsigned length = 0;
    int *array = fileReadIntArray("ints.txt", length);
    result &= (length == 3);
    result &= (array[0] == 1);
    result &= (array[1] == 2);
    result &= (array[2] == 3);
    delete[] array;
    
    return result;
}

bool testWriteWordsFile() {
    bool result = true;
    
    String *one = stringCreate("mama");
    String *two = stringCreate("mila\n");
    String *three = stringCreate("papu");
    String* array[] = {one, two, three};
    int unsigned length = 3;
    FILE *file = fopen("words.txt", "w");
    fileWriteWordsArray(file, array, length);
    fclose(file);
    stringDelete(one);
    stringDelete(two);
    stringDelete(three);
    
    return result;
}

bool testWriteWordsName() {
    bool result = true;
    
    String *one = stringCreate("mama");
    String *two = stringCreate("mila\n");
    String *three = stringCreate("papu");
    String* array[] = {one, two, three};
    int unsigned length = 3;
    fileWriteWordsArray("words.txt", array, length);
    stringDelete(one);
    stringDelete(two);
    stringDelete(three);
    
    return result;
}

bool testReadWordsFile() {
    bool result = true;
    
    FILE *file = fopen("words.txt", "r");
    int unsigned length = 0;
    String **array = fileReadWordsArray(file, length);
    result &= (length == 3);
    String *one = stringCreate("mama");
    result &= stringAreEqual(array[0], one);
    String *two = stringCreate("mila");
    result &= stringAreEqual(array[1], two);
    String *three = stringCreate("papu");
    result &= stringAreEqual(array[2], three);
    
    stringDelete(one);
    stringDelete(two);
    stringDelete(three);
    fclose(file);
    for (int unsigned i = 0; i < length; i++)
        stringDelete(array[i]);
    delete[] array;
    
    return result;
}

bool testReadWordsName() {
    bool result = true;
    
    int unsigned length = 0;
    String **array = fileReadWordsArray("words.txt", length);
    result &= (length == 3);
    String *one = stringCreate("mama");
    result &= stringAreEqual(array[0], one);
    String *two = stringCreate("mila");
    result &= stringAreEqual(array[1], two);
    String *three = stringCreate("papu");
    result &= stringAreEqual(array[2], three);
    
    stringDelete(one);
    stringDelete(two);
    stringDelete(three);
    for (int unsigned i = 0; i < length; i++)
        stringDelete(array[i]);
    delete[] array;
    
    return result;
}

bool testWriteStringsFile() {
    bool result = true;
    
    String *one = stringCreate("mama mila");
    String *two = stringCreate("papu");
    String* array[] = {one, two};
    int unsigned length = 2;
    FILE *file = fopen("strings.txt", "w");
    fileWriteStringsArray(file, array, length);
    fclose(file);
    stringDelete(one);
    stringDelete(two);
    
    return result;
}

bool testWriteStringsName() {
    bool result = true;
    
    String *one = stringCreate("mama mila");
    String *two = stringCreate("papu");
    String* array[] = {one, two};
    int unsigned length = 2;
    fileWriteStringsArray("strings.txt", array, length);
    stringDelete(one);
    stringDelete(two);
    
    return result;
}

bool testReadStringsFile() {
    bool result = true;
    
    FILE *file = fopen("strings.txt", "r");
    int unsigned length = 0;
    String **array = fileReadStringsArray(file, length);
    result &= (length == 2);
    String *one = stringCreate("mama mila");
    result &= stringAreEqual(array[0], one);
    String *two = stringCreate("papu");
    result &= stringAreEqual(array[1], two);
    
    stringDelete(one);
    stringDelete(two);
    fclose(file);
    for (int unsigned i = 0; i < length; i++)
        stringDelete(array[i]);
    delete[] array;
    
    return result;
}

bool testReadStringsName() {
    bool result = true;
    
    int unsigned length = 0;
    String **array = fileReadStringsArray("strings.txt", length);
    result &= (length == 2);
    String *one = stringCreate("mama mila");
    result &= stringAreEqual(array[0], one);
    String *two = stringCreate("papu");
    result &= stringAreEqual(array[1], two);
    
    stringDelete(one);
    stringDelete(two);
    for (int unsigned i = 0; i < length; i++)
        stringDelete(array[i]);
    delete[] array;
    
    return result;
}

int main() {
    char passed[] = "\x1b[32mpassed\x1b[0m";
    char failed[] = "\x1b[31mfailed\x1b[0m";
    
    printf("WriteIntFile test %s!\n", testWriteIntFile() ? passed : failed);
    printf("ReadIntFile test %s!\n", testReadIntFile() ? passed : failed);
    printf("WriteIntName test %s!\n", testWriteIntName() ? passed : failed);
    printf("ReadIntName test %s!\n", testReadIntName() ? passed : failed);
    printf("WriteWordsFile test %s!\n", testWriteWordsFile() ? passed : failed);
    printf("ReadWordsFile test %s!\n", testReadWordsFile() ? passed : failed);
    printf("WriteWordsName test %s!\n", testWriteWordsName() ? passed : failed);
    printf("ReadWordsName test %s!\n", testReadWordsName() ? passed : failed);
    printf("WriteStringsFile test %s!\n", testWriteStringsFile() ? passed : failed);
    printf("ReadStringsFile test %s!\n", testReadStringsFile() ? passed : failed);
    printf("WriteStringsName test %s!\n", testWriteStringsName() ? passed : failed);
    printf("ReadStringsName test %s!\n", testReadStringsName() ? passed : failed);
    
    return 0;
}
