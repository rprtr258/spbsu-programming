#include <string.h>
#include <ctype.h>
#include <stdio.h>
#include "exprtree.h"

struct ExprNode {
    bool isOperator = false;
    char op = '#';
    ExprNode *left = nullptr;
    ExprNode *right = nullptr;
    int value = 0;
};

ExprNode* parseNode(const char *str) {
    ExprNode *node = new ExprNode();
    
    if (str[0] == '(') {
        // 0123
        // (* left right)
        // ^
        node->isOperator = true;
        node->op = str[1];
        
        int j = 4;
        int opened = (str[3] == '(');
        while (opened > 0) {
            opened += (str[j] == '(');
            opened -= (str[j] == ')');
            j++;
        }
        while (str[j] != ' ')
            j++;
        
        node->left = parseNode(str + 3);
        node->right = parseNode(str + j + 1);
    } else {
        // number literal
        bool sign = true;
        int num = 0;
        int i = 0;
        if (str[i] == '-') {
            i++;
            sign = false;
        }
        while (isdigit(str[i])) {
            num = num * 10 + (str[i] - '0');
            i++;
        }
        node->value = num * (sign ? 1 : -1);
    }
    
    return node;
}

ExprTree* parseFromString(const char *str) {
    ExprTree* result = new ExprTree();
    result->root = parseNode(str);
    
    return result;
}

void calculate(ExprNode *node) {
    if (node == nullptr || !node->isOperator)
        return;
    calculate(node->left);
    calculate(node->right);
    int a = node->left->value;
    int b = node->right->value;
    int c = 0;
    switch (node->op) {
        case '+': {
            c = a + b;
            break;
        }
        case '-': {
            c = a - b;
            break;
        }
        case '*': {
            c = a * b;
            break;
        }
        case '/': {
            c = a / b;
            break;
        }
    }
    node->value = c;
}

void calculate(ExprTree *tree) {
    calculate(tree->root);
}

void printInfix(ExprNode *node) {
    if (node == nullptr)
        return;
    if (node->isOperator) {
        printf("(");
        printInfix(node->left);
        printf(" %c ", node->op);
        printInfix(node->right);
        printf(")");
    } else {
        printf("%d", node->value);
    }
}

void printInfix(ExprTree *tree) {
    printInfix(tree->root);
}

int getResult(ExprTree *tree) {
    return tree->root->value;
}

void erase(ExprNode *node) {
    if (node == nullptr)
        return;
    erase(node->left);
    erase(node->right);
    delete node;
}

void erase(ExprTree *tree) {
    erase(tree->root);
    tree->root = nullptr;
}

bool test(const char *str, int result) {
    ExprTree *tree = parseFromString(str);
    calculate(tree);
    
    bool isCorrect = (result == getResult(tree));
    
    erase(tree);
    delete tree;
    
    return isCorrect;
}

bool testExpr(const char *str, int result, const char *message) {
    bool isCorrect = test(str, result);
    if (!isCorrect)
        printf("\"%s\" test failed\n", message);
    return isCorrect;
}
