#pragma once

struct Matrix2x2 {
    int data[2][2] = {
        {0, 0},
        {0, 0}
    };
};

void matrixSet(Matrix2x2 &matrix, const int a00, const int a01, const int a10, const int a11);
Matrix2x2 operator*(const Matrix2x2 &first, const Matrix2x2 &second);

Matrix2x2 pow(const Matrix2x2 &base, const int degree);