#!/bin/bash
echo ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
echo "IntLinkedList tests have started"
echo "==============================="
g++ -Wall -O0 tests/intListTest.cpp intList/intList.cpp
./a
rm a
echo "==============================="
echo "IntLinkedList tests have passed"
echo "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"

