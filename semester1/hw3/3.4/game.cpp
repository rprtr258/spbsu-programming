#include <random>
#include <algorithm>
#include <time.h>
#include <string.h>
#include "game.h"

void gameInit(Game &game) {
    srand(time(nullptr));
    game.running = true;
    game.secret = new int[game.wordLength];
    generateRandomSequence(game.secret, game.wordLength, game.digits);
}

std::pair<int, int> gameCompare(const Game &game, const char *variant) {
    std::pair<int, int> result(0, 0);
    int *integerVariant = new int[game.wordLength];
    for (int i = 0; i < game.wordLength; i++)
        integerVariant[i] = variant[i] - '0';
        
    int guessedPositions = 0;
    int guessedDigits = 0;
    bool maskSecret[game.digits];
    bool maskVariant[game.digits];
    for (int i = 0; i < game.digits; i++) {
        maskSecret[i] = false;
        maskVariant[i] = false;
    }
    
    for (int i = 0; i < game.wordLength; i++) {
        if (integerVariant[i] == game.secret[i])
            guessedPositions++;
            
        maskSecret[game.secret[i]] = true;
        maskVariant[integerVariant[i]] = true;
    }
    
    for (int i = 0; i < game.digits; i++) {
        if (maskSecret[i] && maskVariant[i])
            guessedDigits++;
    }
    // remove digits with equal positions
    guessedDigits -= guessedPositions;
    
    result.first = guessedDigits;
    result.second = guessedPositions;
    
    delete[] integerVariant;
    
    return result;
}

void gameTerminate(Game &game) {
    game.running = false;
    for (int i = 0; i < game.wordLength; i++)
        game.secret[i] = 0;
    delete[] game.secret;
    game.secret = nullptr;
}

void gameRestart(Game &game) {
    generateRandomSequence(game.secret, game.wordLength, game.digits);
}

void generateRandomSequence(int *array, const int length, const int digits) {
    int *temp = new int[digits];
    for (int i = 0; i < digits; i++)
        temp[i] = i;
    std::random_shuffle(temp, temp + digits);
    for (int i = 0; i < length; i++)
        array[i] = temp[i];
    delete[] temp;
}
