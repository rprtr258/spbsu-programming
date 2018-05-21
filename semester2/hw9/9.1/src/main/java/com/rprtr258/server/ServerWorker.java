package com.rprtr258.server;

import com.rprtr258.network.SocketWrapper;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class ServerWorker {
    private Map<String, SocketWrapperWrapper> clients = new TreeMap<>();
    private Map<String, Boolean> confirmationQueue = new TreeMap<>();
    private Map<String, String> messageQueue = new TreeMap<>();

    public void addClient(String clientName, SocketWrapper socketWrapper) {
        if (clients.containsKey(clientName))
            throw new ClientAlreadyAddedException();
        clients.put(clientName, new SocketWrapperWrapper(socketWrapper));
    }

    public String readMessage(String clientName) throws IOException {
        SocketWrapper clientSocket = clients.get(clientName).socketWrapper;
        synchronized (clientSocket) {
            return clientSocket.readMessage();
        }
    }

    public synchronized void sendAll(String message) {
        for (SocketWrapperWrapper client : clients.values())
            client.socketWrapper.sendMessage(message);
    }

    public synchronized void sendTo(String clientName, String message) {
        clients.get(clientName).socketWrapper.sendMessage(message);
    }

    public synchronized void connectConfirm(String clientName) {
        confirmationQueue.put(clientName, true);
        boolean allConfirmed = true;
        for (String client : clients.keySet())
            if (confirmationQueue.containsKey(client))
                allConfirmed &= confirmationQueue.get(client);
            else
                allConfirmed = false;
        if (allConfirmed) {
            messageQueue.forEach(this::sendTo);
            sendAll("connected");
            confirmationQueue.clear();
            messageQueue.clear();
        }
    }

    public void queueTo(String clientName, String message) {
        messageQueue.put(clientName, message);
    }

    private class ClientAlreadyAddedException extends RuntimeException {}

    private class SocketWrapperWrapper {
        SocketWrapper socketWrapper = null;

        private SocketWrapperWrapper(SocketWrapper socketWrapper) {
            this.socketWrapper = socketWrapper;
        }
    }
}
