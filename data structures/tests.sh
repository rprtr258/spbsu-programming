#!/bin/bash
echo ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
echo "IntStack tests have started"
echo "==============================="
g++ -std=c++11 -Wall -O0 tests/intStackTest.cpp intList/intList.cpp intStack/intStack.cpp -o "is_test"
./is_test
rm is_test
echo "==============================="
echo "IntStack tests have passed"
echo "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"

echo ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
echo "Quicksort tests have started"
echo "==============================="
g++ -std=c++11 -Wall -O0 tests/quicksortTest.cpp quicksort/quicksort.cpp -o "qs_test"
./qs_test
rm qs_test
echo "==============================="
echo "Quicksort tests have passed"
echo "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"

echo ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
echo "IntLinkedList tests have started"
echo "==============================="
g++ -std=c++11 -Wall -O0 tests/intListTest.cpp intList/intList.cpp -o "il_test"
./il_test
rm il_test
echo "==============================="
echo "IntLinkedList tests have passed"
echo "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"

