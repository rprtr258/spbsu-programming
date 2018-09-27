package com.rprtr258;

import java.util.*;
import java.util.stream.Collectors;

public class Network {
    private Map<String, List<String>> graph = new TreeMap<>();
    private Map<String, String> userOS = new TreeMap<>();
    private Map<String, Double> osInfectionProbability = new TreeMap<>();
    private List<String> infected = new ArrayList<>();

    public void emulate() {
        printState();
        while (infected.size() < userOS.size()) {
            performWorldStep();
            printState();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Emulation was interrupted");
            }
        }
        System.out.println("ZE WARDO was conquered");
    }

    public void addOS(String name, double security) {
        osInfectionProbability.put(name, 1 - security);
    }

    public void addUser(String name, List<String> neighbours, String os) {
        if (!graph.containsKey(name)) {
            graph.put(name, new ArrayList<>());
        }
        for (String neighbour : neighbours) {
            graph.get(name).add(neighbour);
        }
        userOS.put(name, os);
    }

    public void infect(String name) {
        infected.add(name);
    }

    @Override
    public String toString() {
        final String[] result = {""};
        result[0] += "Users:\n";
        graph.forEach((user, neighbours) -> {
            result[0] += "    name: " + user + "\n";
            result[0] += "    os: " + userOS.get(user) + "\n";
            result[0] += "    neighbours: " + neighbours.stream().collect(Collectors.joining(", ")) + "\n";
            result[0] += "\n";
        });
        result[0] += "OS-s:\n";
        result[0] += osInfectionProbability.entrySet().stream()
                              .map(entry -> "    name: " + entry.getKey() + "\n" +
                                            "    infection prob.: " + entry.getValue() + "\n")
                              .collect(Collectors.joining("\n"));
        return result[0];
    }

    private void printState() {
        userOS.forEach((user, os) ->
            System.out.printf("(%s, %s[%.2f]) - %s\n", user, os, osInfectionProbability.get(os), (infected.contains(user) ? "!!! infected !!!" : "healthy"))
        );
        System.out.println();
    }

    private void performWorldStep() {
        List<String> toInfect = new ArrayList<>();
        for (String infectedUser : infected) {
            for (String target : graph.get(infectedUser)) {
                if (infected.contains(target) && !toInfect.contains(target))
                    continue;
                boolean willBeInfected = diceRoll(osInfectionProbability.get(userOS.get(target)));
                if (willBeInfected) {
                    toInfect.add(target);
                }
            }
        }
        infected.addAll(toInfect);
    }

    private boolean diceRoll(Double probability) {
        Random random = new Random();
        double roll = random.nextDouble();
        return (roll <= probability);
    }
}
