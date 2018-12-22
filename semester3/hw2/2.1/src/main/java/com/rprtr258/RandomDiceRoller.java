package com.rprtr258;

import java.util.Random;

public class RandomDiceRoller implements DiceRoller {
    @Override
    public boolean diceRoll(Double probability) {
        Random random = new Random();
        double roll = random.nextDouble();
        return (roll <= probability);
    }
}
