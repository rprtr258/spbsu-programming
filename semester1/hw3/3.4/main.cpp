#include <stdio.h>
#include <string.h>
#include <ctype.h>
#include "game.h"

enum CommandCode {
    incorrectCode = -1,
    variantCode = 0,
    wrongVariantCode = 1,
    helpCode = 2,
    exitCode = 3
};

CommandCode parseInput(const Game &game, const char *cmd) {
    if (strcmp(cmd, "help") == 0) {
        return helpCode;
    }
    if (strcmp(cmd, "exit") == 0) {
        return exitCode;
    }
    int cmdLength = strlen(cmd);
    if (cmdLength != game.wordLength)
        return incorrectCode;
    for (int i = 0; i < cmdLength; i++) {
        if (!isdigit(cmd[i])) {
            return incorrectCode;
        }
    }
    
    // check for equal digits
    bool check[game.digits];
    for (int i = 0; i < game.digits; i++)
        check[i] = false;
    for (int i = 0; i < cmdLength; i++) {
        if (check[cmd[i] - '0'])
            return wrongVariantCode;
        check[cmd[i] - '0'] = true;
    }
    
    return variantCode;
}

int main() {
    printf("Bulls and Cows game. Type \"help\" to read rules, \"exit\" to quit.\n");
    Game mainGame;
    gameInit(mainGame);
    printf("Game started, current word size: %d\n", mainGame.wordLength);
    while (mainGame.running) {
        char cmd[10];
        printf("Write variant: ");
        scanf("%s", cmd);
        CommandCode code = parseInput(mainGame, cmd);
        switch (code) {
            case helpCode: {
                printf("You should guess %d-digits number in this game. You can write your variants "
                       "as number of length %d without spaces(for example 93246 for 5 digits)\n"
                       "More info at en.wikipedia.org/wiki/Bulls_and_Cows\n", mainGame.wordLength, mainGame.wordLength);
                break;
            }
            case exitCode: {
                gameTerminate(mainGame);
                break;
            }
            case variantCode: {
                std::pair<int, int> result = gameCompare(mainGame, cmd);
                printf("%d cows and %d bulls\n", result.first, result.second);
                if (result.second == mainGame.wordLength) {
                    printf("You guessed secret word! It was %s\n", cmd);
                    gameRestart(mainGame);
                }
                break;
            }
            case incorrectCode: {
                printf("Sorry, I couldn't get it, please retype\n");
                break;
            }
            case wrongVariantCode: {
                printf("It surely can't be answer, try other sequence\n");
                break;
            }
        }
    }
    return 0;
}
