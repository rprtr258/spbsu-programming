#include "matrix2x2.h"

Matrix2x2 operator*(const Matrix2x2 &first, const Matrix2x2 &second) {
    Matrix2x2 result;
    for (int i = 0;i < 2; i++)
        for (int j = 0; j < 2; j++)
            for (int k = 0; k < 2; k++)
                result.data[i][j] += first.data[i][k] * second.data[k][j];
    return result;
}

void matrixSet(Matrix2x2 &matrix, const int a00, const int a01, const int a10, const int a11) {
    matrix.data[0][0] = a00;
    matrix.data[0][1] = a01;
    matrix.data[1][0] = a10;
    matrix.data[1][1] = a11;
}

Matrix2x2 pow(const Matrix2x2 &base, const int degree) {
    if (degree == 0) {
        Matrix2x2 unite;
        matrixSet(unite, 1, 0, 0, 1);
        return unite;
    }
    if (degree == 1)
        return base;
    if (degree == 2)
        return base * base;
    if (degree % 2 == 0)
        return pow(pow(base, degree / 2), 2);
    // degree % 2 == 1
    return pow(pow(base, (degree - 1) / 2), 2) * base;
}