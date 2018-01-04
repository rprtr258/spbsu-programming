#!/bin/bash
printf ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n"
printf "Heap tests have started\n"
printf "==============================================================\n"
g++ -std=c++11 -Wpedantic -Wextra -Wall -O0 tests/heapTest.cpp heap/heap.cpp -o "ih_test"
./ih_test
CODE="$?"
sleep 1
rm ih_test
printf "==============================================================\n"
printf "Heap tests have passed\n"
printf "Exit code: %d\n" $CODE
printf "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n\n"

printf ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n"
printf "Fileops tests have started\n"
printf "==============================================================\n"
g++ -std=c++11 -Wpedantic -Wextra -Wall -O0 tests/fileopsTest.cpp string/string.cpp fileops/fileops.cpp -o "fo_test"
./fo_test
CODE="$?"
sleep 1
rm fo_test
rm ints.txt
rm strings.txt
rm words.txt
printf "==============================================================\n"
printf "Fileops tests have passed\n"
printf "Exit code: %d\n" $CODE
printf "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n\n"

printf ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n"
printf "String tests have started\n"
printf "==============================================================\n"
g++ -std=c++11 -Wpedantic -Wextra -Wall -O0 tests/stringTest.cpp string/string.cpp -o "s_test"
./s_test
CODE="$?"
sleep 1
rm s_test
printf "==============================================================\n"
printf "String tests have passed\n"
printf "Exit code: %d\n" $CODE
printf "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n\n"

printf ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n"
printf "StringLinkedList tests have started\n"
printf "==============================================================\n"
g++ -std=c++11 -Wpedantic -Wextra -Wall -O0 string/string.cpp tests/stringListTest.cpp stringList/stringList.cpp -o "sl_test"
./sl_test
CODE="$?"
sleep 1
rm sl_test
printf "==============================================================\n"
printf "StringLinkedList tests have passed\n"
printf "Exit code: %d\n" $CODE
printf "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n\n"

printf ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n"
printf "Hashtable tests have started\n"
printf "==============================================================\n"
g++ -std=c++11 -Wpedantic -Wextra -Wall -O0 string/string.cpp tests/hashtableTest.cpp stringList/stringList.cpp hashtable/hashtable.cpp -o "ht_test"
./ht_test
CODE="$?"
sleep 1
rm ht_test
printf "==============================================================\n"
printf "Hashtable tests have passed\n"
printf "Exit code: %d\n" $CODE
printf "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n\n"

printf ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n"
printf "IntLinkedList tests have started\n"
printf "==============================================================\n"
g++ -std=c++11 -Wpedantic -Wextra -Wall -O0 tests/intListTest.cpp intList/intList.cpp -o "il_test"
./il_test
CODE="$?"
sleep 1
rm il_test
printf "==============================================================\n"
printf "IntLinkedList tests have passed\n"
printf "Exit code: %d\n" $CODE
printf "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n\n"

printf ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n"
printf "IntStack tests have started\n"
printf "==============================================================\n"
g++ -std=c++11 -Wpedantic -Wextra -Wall -O0 tests/intStackTest.cpp intList/intList.cpp intStack/intStack.cpp -o "is_test"
./is_test
CODE="$?"
sleep 1
rm is_test
printf "==============================================================\n"
printf "IntStack tests have passed\n"
printf "Exit code: %d\n" $CODE
printf "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n\n"

printf ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n"
printf "IntQueue tests have started\n"
printf "==============================================================\n"
g++ -std=c++11 -Wpedantic -Wextra -Wall -O0 tests/intQueueTest.cpp intList/intList.cpp intQueue/intQueue.cpp -o "iq_test"
./iq_test
CODE="$?"
sleep 1
rm iq_test
printf "==============================================================\n"
printf "IntQueue tests have passed\n"
printf "Exit code: %d\n" $CODE
printf "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n\n"

printf ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n"
printf "Quicksort tests have started\n"
printf "==============================================================\n"
g++ -std=c++11 -Wpedantic -Wextra -Wall -O0 tests/quicksortTest.cpp quicksort/quicksort.cpp -o "qs_test"
./qs_test
CODE="$?"
sleep 1
rm qs_test
printf "==============================================================\n"
printf "Quicksort tests have passed\n"
printf "Exit code: %d\n" $CODE
printf "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n\n"

exit 0

