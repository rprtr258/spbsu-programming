package com.rprtr258;

import java.util.*;
import java.util.stream.Collectors;

public class NetworkFactory {
    static public Network createNetwork(String config) {
        Network result = new Network();
        EntryType entryType = null;
        String name = null;
        double security = 0;
        List<String> neighbours = null;
        String os = null;
        boolean isUserInfected = false;
        for (String token : config.split("\\r?\\n")) {
            int i = 0;
            while (token.charAt(i) == ' ')
                i++;
            token = token.substring(i);
            if ("{".equals(token)) {
                isUserInfected = false;
            } else if ("}".equals(token)) {
                switch (entryType) {
                    case OSEntry: {
                        result.addOS(name, security);
                        break;
                    }
                    case UserEntry:
                        result.addUser(name, neighbours, os);
                        break;
                }
                if (isUserInfected)
                    result.infect(name);
            } else {
                String tokenType = token.substring(0, token.indexOf(':'));
                String param = token.substring(token.indexOf(':') + 2);
                switch(tokenType) {
                    case "type": {
                        switch(param) {
                            case "os": {
                                entryType = EntryType.OSEntry;
                                break;
                            }
                            case "user": {
                                entryType = EntryType.UserEntry;
                                break;
                            }
                            default: {
                                System.out.printf("Wrong token type found: %s\n", param);
                            }
                        }
                        break;
                    }
                    case "name": {
                        name = param.substring(1, param.length() - 1);
                        break;
                    }
                    case "security": {
                        security = Double.parseDouble(param);
                        break;
                    }
                    case "neighbours": {
                        String neighboursList = param.substring(1, param.length() - 1);
                        if (!neighboursList.isEmpty()) {
                            neighbours = Arrays.stream(neighboursList.split(", "))
                                    .map(s -> s.substring(1, s.length() - 1))
                                    .collect(Collectors.toList());
                        } else {
                            neighbours = new ArrayList<>();
                        }
                        break;
                    }
                    case "os": {
                        os = param.substring(1, param.length() - 1);
                        break;
                    }
                    case "infected": {
                        isUserInfected = Boolean.parseBoolean(param);
                        break;
                    }
                    default: {
                        System.out.printf("Wrong token type: %s\n", tokenType);
                    }
                }
            }
        }
        return result;
    }
}

enum EntryType {
    OSEntry,
    UserEntry
}
