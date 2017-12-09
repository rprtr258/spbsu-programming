#pragma once

struct ExprNode;

struct ExprTree {
    ExprNode *root = nullptr;
};

ExprTree* parseFromString(const char *str);
void calculate(ExprTree *tree);
void printInfix(ExprTree *tree);
int getResult(ExprTree *tree);

void erase(ExprTree *tree);

bool test(const char *str, int result);
bool testExpr(const char *str, int result, const char *message);