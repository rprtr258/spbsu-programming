#include <stdio.h>
#include "../string/string.h"
#include "../hashtable/hashtable.h"

bool testCreationDeletion() {
    bool result = true;
    
    HashTable *temp1 = hashTableCreate();
    hashTableDelete(temp1);
    
    HashTable *temp2 = hashTableCreate();
    String *one = stringCreate("1");
    hashTableInsert(temp2, one);
    hashTableDelete(temp2);
    stringDelete(one);
    
    return result;
}

bool testPrintEmpty() {
    bool result = true;
    
    printf("Empty hashtable:\n");
    HashTable *temp = hashTableCreate();
    hashTablePrint(temp);
    hashTableDelete(temp);
    
    return result;
}

bool testPrintNonEmpty() {
    bool result = true;
    
    printf("Non-empty hashtable:\n");
    HashTable *temp = hashTableCreate();
    hashTableInsert(temp, "lol");
    hashTableInsert(temp, "kek");
    hashTableInsert(temp, "cheburek");
    hashTableInsert(temp, "");
    hashTableInsert(temp, "2434");
    hashTableInsert(temp, "password");
    hashTableInsert(temp, "url");
    hashTablePrint(temp);
    hashTableDelete(temp);
    
    return result;
}

bool testPrint() {
    bool result = true;
    
    result &= testPrintEmpty();
    result &= testPrintNonEmpty();
    
    return result;
}

bool testInsert() {
    bool result = true;
    
    HashTable *temp = hashTableCreate();
    hashTableInsert(temp, "ffajlru");
    hashTableInsert(temp, "fp43987yhonf");
    hashTableInsert(temp, "");
    result &= (temp->size >= 1);
    hashTableDelete(temp);
    
    return result;
}

bool testContains() {
    bool result = true;
    
    HashTable *temp = hashTableCreate();
    hashTableInsert(temp, "");
    hashTableInsert(temp, "a");
    hashTableInsert(temp, "2");
    result &= hashTableContains(temp, "");
    result &= hashTableContains(temp, "a");
    result &= hashTableContains(temp, "2");
    result &= (!hashTableContains(temp, "1"));
    hashTableDelete(temp);
    
    return result;
}

int main() {
    char passed[] = "\x1b[32mpassed\x1b[0m";
    char failed[] = "\x1b[31mfailed\x1b[0m";
    printf("CreationDeletion test %s!\n", testCreationDeletion() ? passed : failed);
    printf("Insert test %s!\n", testInsert() ? passed : failed);
    printf("Print test %s!\n", testPrint() ? passed : failed);
    printf("Find test %s!\n", testContains() ? passed : failed);
    
    return 0;
}
