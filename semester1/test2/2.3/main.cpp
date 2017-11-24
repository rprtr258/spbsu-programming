#include <stdio.h>
#include <queue>

void writeQueue(FILE *file, std::queue<int> &row) {
    while (!row.empty()) {
        fprintf(file, "%d", row.front());
        row.pop();
        if (!row.empty())
            fprintf(file, " ");
    }
    fprintf(file, "\n");
}

int main() {
    int a = 0, b = 0;
    printf("Write a, b: ");
    scanf("%d %d", &a, &b);
    
    FILE *fileF = fopen("f", "r");
    std::queue<int> q1, q2, q3;
    
    while (!feof(fileF)) {
        int x;
        fscanf(fileF, "%d", &x);
        if (x < a) {
            q1.push(x);
        } else if (b < x) {
            q3.push(x);
        } else {
            q2.push(x);
        }
    }
    fclose(fileF);
    
    FILE *fileG = fopen("g", "w");
    
    writeQueue(fileG, q1);
    writeQueue(fileG, q2);
    writeQueue(fileG, q3);
    
    fclose(fileG);
    return 0;
}
