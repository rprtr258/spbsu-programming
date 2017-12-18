#include <stdio.h>
#include <string.h>

struct Person {
    char secondName[1000];
    int loyality;
};

void insertPerson(Person **&list, int &listSize, const char *secondName, int const loyality) {
    Person *newPerson = new Person();
    strcpy(newPerson->secondName, secondName);
    newPerson->loyality = loyality;
    
    Person **newList = new Person*[listSize + 1];
    newList[listSize] = newPerson;
    if (list != nullptr) {
        for (int i = 0; i < listSize; i++) {
            newList[i] = new Person();
            memcpy(newList[i], list[i], sizeof(Person));
        }
        for (int i = 0; i < listSize; i++)
            delete list[i];
        delete[] list;
    }
    list = newList;
    listSize++;
}

void sortByLoyality(Person **list, int const listSize) {
    for (int i = 0; i < listSize; i++)
        for (int j = 0; j < i; j++)
            if (list[i]->loyality < list[j]->loyality) {
                Person *temp = list[i];
                list[i] = list[j];
                list[j] = temp;
            }
}

void sortBySecondName(Person **list, int const listSize) {
    for (int i = 0; i < listSize; i++)
        for (int j = 0; j < i; j++)
            if (strcmp(list[i]->secondName, list[j]->secondName) < 0) {
                Person *temp = list[i];
                list[i] = list[j];
                list[j] = temp;
            }
}

void printList(Person **list, int const listSize) {
    for (int i = 0; i < listSize; i++)
        printf("%s(%d)\n", list[i]->secondName, list[i]->loyality);
}

int main() {
    FILE *file = fopen("list.txt", "r");
    if (file == NULL) {
        printf("List of people not found\n");
        return 0;
    }
    
    int killCount = 0;
    printf("Write how many people you want to kill:\n");
    scanf("%d", &killCount);
    
    int sendCount = 0;
    printf("Write how many people you want to send to Siberia:\n");
    scanf("%d", &sendCount);
    
    Person **list = nullptr;
    int listSize = 0;
    
    while (!feof(file)) {
        char secondName[101];
        int loyality = -1;
        fscanf(file, "%100s - %d", secondName, &loyality);
        if (feof(file))
            break;
        
        // printf("READ: %s(%d)\n", secondName, loyality);
        insertPerson(list, listSize, secondName, loyality);
    }
    if (killCount + sendCount > listSize || killCount < 0 || sendCount < 0) {
        printf("Sorry, you want to kill/send too many people\n");
        if (list != nullptr) {
            for (int i = 0; i < listSize; i++)
                delete list[i];
            delete[] list;
        }
        fclose(file);
        return 0;
    }
    sortByLoyality(list, listSize);
    sortBySecondName(list + killCount, listSize - killCount);
    //printList(list, listSize);
    
    printf("\nPeople to execute:\n");
    for (int i = 0; i < killCount; i++)
        printf("%s\n", list[i]->secondName);
    
    printf("\nPeople to send to Siberia:\n");
    for (int i = 0; i < sendCount; i++)
        printf("%s\n", list[i + killCount]->secondName);
    
    printf("\nPeople staying alive:\n");
    for (int i = 0; i < listSize - (killCount + sendCount); i++)
        printf("%s\n", list[i + killCount + sendCount]->secondName);
    
    if (list != nullptr) {
        for (int i = 0; i < listSize; i++)
            delete list[i];
        delete[] list;
    }
    fclose(file);
    return 0;
}
