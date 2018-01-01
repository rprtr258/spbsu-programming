#!/bin/bash
echo ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
echo "IntLinkedList tests have started"
echo "==============================="
g++ -std=c++11 -Wall -O0 tests/intListTest.cpp intList/intList.cpp
./a
rm a
echo "==============================="
echo "IntLinkedList tests have passed"
echo "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"

echo ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
echo "Quicksort tests have started"
echo "==============================="
g++ -std=c++11 -Wall -O0 tests/quicksortTest.cpp quicksort/quicksort.cpp
./a
rm a
echo "==============================="
echo "Quicksort tests have passed"
echo "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"

