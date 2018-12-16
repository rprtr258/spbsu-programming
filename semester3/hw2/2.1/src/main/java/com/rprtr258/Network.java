package com.rprtr258;

import java.util.*;
import java.util.stream.Collectors;

public class Network {
    private Map<String, List<String>> graph = new TreeMap<>();
    private Map<String, String> userOS = new TreeMap<>();
    private Map<String, Double> osInfectionProbability = new TreeMap<>();
    private List<String> infected = new ArrayList<>();

    public String emulate() {
        StringBuilder report = new StringBuilder();
        report.append(getState());
        while (infected.size() < userOS.size()) {
            performWorldStep();
            report.append(getState());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                report.append("Emulation was interrupted\n");
            }
        }
        report.append("World was conquered\n");
        return report.toString();
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

    public void performWorldStep() {
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

    public String getState() {
        return userOS.entrySet().stream().map(x ->
                String.format("(%s, %s[%.2f]) - %s\n", x.getKey(), x.getValue(), osInfectionProbability.get(x.getValue()), (infected.contains(x.getKey()) ? "!!! infected !!!" : "healthy"))
        ).collect(Collectors.joining());
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

    private boolean diceRoll(Double probability) {
        Random random = new Random();
        double roll = random.nextDouble();
        return (roll <= probability);
    }
}
