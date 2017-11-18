#include <stdio.h>
#include "dsu.h"

int main() {
    printf("Students homework analyzer\n");
    printf("Write number of students: ");
    int students = 0;
    scanf("%d", &students);
    DSU *dsu = createDSU(students + 1);
    
    printf("Write pairs \"x y\" if x-th student copied y-th student work:\n");
    printf("(Ctrl-X to end input)\n");
    int idRecipient = 0;
    int idDonor = 0;
    while (scanf("%d %d", &idRecipient, &idDonor))
        merge(dsu, idRecipient, idDonor);
    
    for (int id = 1; id <= 3; id++)
        printf("%d-th student made unique work\n", id);
    for (int id = 4; id <= students; id++) {
        int idWork = getSetIndex(dsu, id);
        if (idWork == id)
            printf("%d-th student haven't done homework\n", id);
        else
            printf("%d-th student copied %d-th student's homework\n", id, idWork);
    }
    
    erase(dsu);
    delete dsu;
    return 0;
}
