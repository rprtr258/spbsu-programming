#!/bin/bash
printf "Building program\n"
make
printf "Launching a.exe\n"
./a
CODE="$?"
sleep 1
rm a
printf "Exit code: %d\n" $CODE

exit 0

