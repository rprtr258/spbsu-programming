package com.rprtr258.client;

import com.rprtr258.network.Client;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ConnectionWindowController {
    public TextField hostTextField;
    public TextField portTextField;
    public Button connectButton;
    public Label statusLabel;

    public void initialize() {
        connectButton.setOnAction((actionEvent) -> onConnect());
    }

    private void onConnect() {
        String host = hostTextField.getText();
        String portString = portTextField.getText();
        if (!checkIpAddress(host) && !"localhost".equals(host)) {
            statusLabel.setText("Incorrect host " + host);
            return;
        }
        int port = Integer.parseInt(portString);
        if (port < 0 || port > 65535) {
            statusLabel.setText("Incorrect port " + port);
            return;
        }
        statusLabel.setText("Trying to connect given server");
        connectButton.setDisable(true);
        hostTextField.setDisable(true);
        portTextField.setDisable(true);
        Client client = new Client();
        client.setOnLostConnection(() -> statusLabel.setText("Lost connection to server"));
        new Thread(() -> client.tryConnectServer(host, port, (playerName) -> launchMainWindow(playerName, client), () -> {
            statusLabel.setText("Failed to connect");
            connectButton.setDisable(false);
            hostTextField.setDisable(false);
            portTextField.setDisable(false);
        })).start();
    }

    private void launchMainWindow(String playerName, Client client) {
        Stage thisStage = (Stage)connectButton.getScene().getWindow();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
            Scene scene = new Scene(loader.load());
            MainWindowController controller = loader.getController();
            controller.setClient(client);
            controller.setPlayerName(playerName);
            thisStage.setScene(scene);
            thisStage.setTitle("Tic-Tac-Toe");
            thisStage.setResizable(false);
            thisStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean checkIpAddress(String ip) {
        String[] octets = ip.split("\\.");
        if (octets.length != 4)
            return false;
        for (String octet : octets) {
            if (octet.startsWith("0") && !"0".equals(octet)) {
                return false;
            }
        }
        for (String octet : octets) {
            int number = Integer.parseInt(octet);
            if (number < 0 || number > 255)
                return false;
        }
        return true;
    }
}
