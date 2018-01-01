#!/bin/bash
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
g++ -std=c++11 -Wall -O0 tests/intListTest.cpp intList/intList.cpp
./a
rm a
echo "==============================="
echo "IntLinkedList tests have passed"
echo "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"

