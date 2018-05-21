package com.rprtr258.server;

import com.rprtr258.network.SocketWrapper;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class ServerWorker {
    private Map<String, SocketWrapper> clients = new TreeMap<>();

    public void addClient(String clientName, SocketWrapper socketWrapper) {
        if (clients.containsKey(clientName))
            throw new ClientAlreadyAddedException();
        clients.put(clientName, socketWrapper);
    }

    public String readMessage(String clientName) throws IOException {
        return clients.get(clientName).readMessage();
    }

    public void sendAll(String message) {
        for (SocketWrapper client : clients.values())
            client.sendMessage(message);
    }

    public void sendTo(String clientName, String message) {
        clients.get(clientName).sendMessage(message);
    }

    private class ClientAlreadyAddedException extends RuntimeException {}
}
