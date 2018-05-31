package com.rprtr258.server;

import com.rprtr258.network.MessagesProcessor;
import com.rprtr258.network.SocketWrapper;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

/**
 * Object which manipulates connected to server clients.
 */
public class ServerWorker {
    private Map<String, SocketWrapper> clients = new TreeMap<>();
    private Map<String, Boolean> confirmationQueue = new TreeMap<>();
    private Map<String, String> messageQueue = new TreeMap<>();

    /**
     * Adds connected client.
     *
     * @param clientName client known name.
     * @param socketWrapper socket to talk to him.
     */
    public void addClient(String clientName, SocketWrapper socketWrapper) {
        if (clients.containsKey(clientName))
            throw new ClientAlreadyAddedException();
        clients.put(clientName, socketWrapper);
    }

    /**
     * Reads message from a client.
     *
     * @param clientName client name.
     * @return message read from a client.
     * @throws IOException if occurred.
     */
    public String readMessage(String clientName) throws IOException {
        SocketWrapper clientSocket = clients.get(clientName);
        synchronized (clientSocket) {
            return clientSocket.readMessage();
        }
    }

    /**
     * Sends message to all clients.
     *
     * @param message message to send.
     */
    public synchronized void sendAll(String message) {
        for (SocketWrapper client : clients.values())
            client.sendMessage(message);
    }

    /**
     * Sends message to specific client.
     *
     * @param clientName client name.
     * @param message message to send.
     */
    public synchronized void sendTo(String clientName, String message) {
        clients.get(clientName).sendMessage(message);
    }

    /**
     * Confirm client for connection. If all clients are confirmed, connect message is send
     * to all of them and then queued messages to clients are sent if any present.
     *
     * @param clientName client name.
     */
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
            sendAll(MessagesProcessor.CONNECT_REQUEST);
            confirmationQueue.clear();
            messageQueue.clear();
        }
    }

    /**
     * Queues message to client.
     *
     * @param clientName client name.
     * @param message message to queue.
     */
    public void queueTo(String clientName, String message) {
        messageQueue.put(clientName, message);
    }

    private class ClientAlreadyAddedException extends RuntimeException {}
}
