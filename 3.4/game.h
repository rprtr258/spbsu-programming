#pragma once
#include <utility>

struct Game {
    const int wordLength = 4;
    const int digits = 10;
    bool running = false;
    int *secret = nullptr;
};

void gameInit(Game &game);
std::pair<int, int> gameCompare(const Game &gameCompare, const char *variant);
void gameTerminate(Game &game);
void gameRestart(Game &game);

void generateRandomSequence(int *array, const int length, const int digits);
