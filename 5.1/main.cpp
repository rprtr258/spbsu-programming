#include <stdio.h>
#include <string>
#include <algorithm>

using std::string;

long long unsigned const signMask = 0x8000000000000000;
long long unsigned const expoMask = 0x7FF0000000000000;
long long unsigned const mantMask = 0x000FFFFFFFFFFFFF;
int const expoSize = 11;
int const mantSize = 52;
char const nullChar = char(0);

string add(string a, string b) {
    if (a.length() < b.length())
        std::swap(a, b);
    string res = a + '0';
    int carry = 0;
    for (size_t i = 0; i < b.length() + 1; i++) {
        if (i < b.length())
            res[i] = res[i] - '0' + b[i] + carry;
        else
            res[i] = res[i] + carry;
        carry = (res[i] - '0') / 10;
        res[i] = (res[i] - '0') % 10 + '0';
    }
    if (res.back() == '0' && res.length() > 1)
        res = res.substr(0, res.length() - 1);
    return res;
}

void printBinary(const long long &x) {
    long long unsigned bit = 0x8000000000000000;
    for (int i = 0; i < 64; i++) {
        printf(x & bit ? "1" : "0");
        bit >>= 1;
    }
    printf("\n");
}

string operator*(const string &x, const int &n) {
    string res = x;
    for (int i = 0; i < n - 1; i++)
        res = add(res, x);
    return res;
}

void parse(double &y, bool &sign, long long &exponent, string &mantissa, int &zeroes) {
    long long *xPointer = (long long*)(&y);
    long long x = *xPointer;
//    printf("x(%I64X):\n", x);
//    printBinary(x);
    sign = ((x & signMask) != 0);
    exponent = ((x & expoMask) >> mantSize) - ((1 << (expoSize - 1)) - 1);
//    printf("expo(%I64d, %I64X): ", exponent, exponent);
//    printBinary(exponent);
    long long mantReversed = (x & mantMask) << (expoSize + 1);
//    printf("mantrev(%I64X): ", mantReversed);
//    printBinary(mantReversed);
    long long unsigned bit = 0x8000000000000000;
    string degree5 = "5";
    while (mantReversed) {
        zeroes++;
        if (mantissa != "0")
            mantissa = "0" + mantissa;
        if (mantReversed & bit)
            mantissa = add(mantissa, degree5);
        degree5 = degree5 * 5;
        mantReversed &= ~bit;
        bit >>= 1;
    }
//    printf("mant(%I64d, %I64X): ", mantissa, mantissa);
//    printBinary(mantissa);
}

int main() {
    double x = 0.0;
    printf("Input double: ");
    scanf("%lf", &x);

    bool sign = false;
    long long exponent = 0;
    string mantissa = "0";
    int zeroes = 0;
    parse(x, sign, exponent, mantissa, zeroes);
    
    zeroes = std::max(0, zeroes - (int) mantissa.length());
    reverse(mantissa.begin(), mantissa.end());
    mantissa = string(zeroes, '0') + mantissa;
    
    printf("Result: %c1.%s * 2 ^ %-I64d", sign ? '-' : '+', mantissa.c_str(), exponent);
    return 0;
}
