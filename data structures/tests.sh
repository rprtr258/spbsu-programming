#!/bin/bash
printf ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n"
printf "IntStack tests have started\n"
printf "===============================\n"
g++ -std=c++11 -Wall -O0 tests/intStackTest.cpp intList/intList.cpp intStack/intStack.cpp -o "is_test"
./is_test
sleep 1
rm is_test
printf "===============================\n"
printf "IntStack tests have passed\n"
printf "Exit code: %d\n" $?
printf "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n\n"

printf ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n"
printf "Quicksort tests have started\n"
printf "===============================\n"
g++ -std=c++11 -Wall -O0 tests/quicksortTest.cpp quicksort/quicksort.cpp -o "qs_test"
./qs_test
sleep 1
rm qs_test
printf "===============================\n"
printf "Quicksort tests have passed\n"
printf "Exit code: %d\n" $?
printf "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n\n"

printf ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n"
printf "IntLinkedList tests have started\n"
printf "===============================\n"
g++ -std=c++11 -Wall -O0 tests/intListTest.cpp intList/intList.cpp -o "il_test"
./il_test
sleep 1
rm il_test
printf "===============================\n"
printf "IntLinkedList tests have passed\n"
printf "Exit code: %d\n" $?
printf "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n\n"

exit 0

