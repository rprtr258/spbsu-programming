package com.rprtr258;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class NetworkTest {
    @Test
    public void toStringTest() {
        Network network = getSampleNetwork();
        assertEquals("Users:\n" +
                              "    name: A\n" +
                              "    os: Windows\n" +
                              "    neighbours: B\n" +
                              "\n" +
                              "    name: B\n" +
                              "    os: Linux\n" +
                              "    neighbours: A, C\n" +
                              "\n" +
                              "    name: C\n" +
                              "    os: DOS\n" +
                              "    neighbours: B, Internet\n" +
                              "\n" +
                              "    name: Internet\n" +
                              "    os: RKN\n" +
                              "    neighbours: C\n" +
                              "\n" +
                              "OS-s:\n" +
                              "    name: DOS\n" +
                              "    infection prob.: 0.9\n" +
                              "\n" +
                              "    name: Linux\n" +
                              "    infection prob.: 0.09999999999999998\n" +
                              "\n" +
                              "    name: RKN\n" +
                              "    infection prob.: 1.0\n" +
                              "\n" +
                              "    name: Windows\n" +
                              "    infection prob.: 0.7\n", network.toString());
    }

    @Test
    public void testEmulation() {
        TestDiceRoller diceRoller = new TestDiceRoller();
        Network network = getSampleNetwork();
        assertEquals("(A, Windows[0,70]) - healthy\n" +
            "(B, Linux[0,10]) - healthy\n" +
            "(C, DOS[0,90]) - healthy\n" +
            "(Internet, RKN[1,00]) - !!! infected !!!\n", network.getState());
        network.performWorldStep(diceRoller);
        assertEquals("(A, Windows[0,70]) - healthy\n" +
                "(B, Linux[0,10]) - healthy\n" +
                "(C, DOS[0,90]) - !!! infected !!!\n" +
                "(Internet, RKN[1,00]) - !!! infected !!!\n", network.getState());
        network.performWorldStep(diceRoller);
        assertEquals("(A, Windows[0,70]) - healthy\n" +
                "(B, Linux[0,10]) - healthy\n" +
                "(C, DOS[0,90]) - !!! infected !!!\n" +
                "(Internet, RKN[1,00]) - !!! infected !!!\n", network.getState());
        network.performWorldStep(diceRoller);
        assertEquals("(A, Windows[0,70]) - healthy\n" +
                "(B, Linux[0,10]) - healthy\n" +
                "(C, DOS[0,90]) - !!! infected !!!\n" +
                "(Internet, RKN[1,00]) - !!! infected !!!\n", network.getState());
        network.performWorldStep(diceRoller);
        assertEquals("(A, Windows[0,70]) - healthy\n" +
                "(B, Linux[0,10]) - healthy\n" +
                "(C, DOS[0,90]) - !!! infected !!!\n" +
                "(Internet, RKN[1,00]) - !!! infected !!!\n", network.getState());
        network.performWorldStep(diceRoller);
        assertEquals("(A, Windows[0,70]) - healthy\n" +
                "(B, Linux[0,10]) - !!! infected !!!\n" +
                "(C, DOS[0,90]) - !!! infected !!!\n" +
                "(Internet, RKN[1,00]) - !!! infected !!!\n", network.getState());
        network.performWorldStep(diceRoller);
        assertEquals("(A, Windows[0,70]) - !!! infected !!!\n" +
                "(B, Linux[0,10]) - !!! infected !!!\n" +
                "(C, DOS[0,90]) - !!! infected !!!\n" +
                "(Internet, RKN[1,00]) - !!! infected !!!\n", network.getState());
    }

    private Network getSampleNetwork() {
        return NetworkFactory.createNetwork("{\n" +
                                  "    type: os\n" +
                                  "    name: \"Windows\"\n" +
                                  "    security: 0.3\n" +
                                  "}\n" +
                                  "{\n" +
                                  "    type: os\n" +
                                  "    name: \"Linux\"\n" +
                                  "    security: 0.9\n" +
                                  "}\n" +
                                  "{\n" +
                                  "    type: os\n" +
                                  "    name: \"DOS\"\n" +
                                  "    security: 0.1\n" +
                                  "}\n" +
                                  "{\n" +
                                  "    type: os\n" +
                                  "    name: \"RKN\"\n" +
                                  "    security: 0\n" +
                                  "}\n" +
                                  "{\n" +
                                  "    type: user\n" +
                                  "    name: \"A\"\n" +
                                  "    neighbours: [\"B\"]\n" +
                                  "    os: \"Windows\"\n" +
                                  "}\n" +
                                  "{\n" +
                                  "    type: user\n" +
                                  "    name: \"B\"\n" +
                                  "    neighbours: [\"A\", \"C\"]\n" +
                                  "    os: \"Linux\"\n" +
                                  "}\n" +
                                  "{\n" +
                                  "    type: user\n" +
                                  "    name: \"C\"\n" +
                                  "    neighbours: [\"B\", \"Internet\"]\n" +
                                  "    os: \"DOS\"\n" +
                                  "}\n" +
                                  "{\n" +
                                  "    type: user\n" +
                                  "    name: \"Internet\"\n" +
                                  "    neighbours: [\"C\"]\n" +
                                  "    os: \"RKN\"\n" +
                                  "    infected: true\n" +
                                  "}\n");
    }

    private class TestDiceRoller implements DiceRoller {
        private List<Double> rolls = Arrays.asList(0.0, 0.1, 0.5, 1.0);
        private int index = 0;
        
        @Override
        public boolean diceRoll(Double probability) {
            boolean result = (rolls.get(index) <= probability);
            index = (index + 1) % rolls.size();
            return result;
        }
    }
}
