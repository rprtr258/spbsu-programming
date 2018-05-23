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
        connectButton.setOnAction((actionEvent) -> onAttemptConnect());
    }

    private void onAttemptConnect() {
        String host = hostTextField.getText();
        String portString = portTextField.getText();
        if (!isCorrectHost(host)) {
            statusLabel.setText("Incorrect host " + host);
            return;
        }
        int port = Integer.parseInt(portString);
        if (!isCorrectPort(port)) {
            statusLabel.setText("Incorrect port " + port);
            return;
        }
        statusLabel.setText("Waiting for other player to connect");
        setElementsDisable(true);
        tryConnect(host, port);
    }

    private boolean isCorrectHost(String host) {
        return checkIpAddress(host) || "localhost".equals(host);
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

    private boolean isCorrectPort(int port) {
        return (0 <= port && port < 65536);
    }

    private void tryConnect(String host, int port) {
        Client client = new Client();
        client.configure(() -> statusLabel.setText("Lost connection to server"), () -> {});
        client.tryConnectServer(host, port, (playerName) -> launchMainWindow(playerName, client), () -> {
            statusLabel.setText("Failed to connect");
            setElementsDisable(false);
        });
    }

    private void setElementsDisable(boolean value) {
        connectButton.setDisable(value);
        hostTextField.setDisable(value);
        portTextField.setDisable(value);
    }

    private void launchMainWindow(String playerName, Client client) {
        Stage thisStage = (Stage)connectButton.getScene().getWindow();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
            Scene scene = new Scene(loader.load());
            MainWindowController controller = loader.getController();
            controller.configure(playerName, client);
            thisStage.setScene(scene);
            thisStage.setTitle("Tic-Tac-Toe");
            thisStage.setResizable(false);
            thisStage.setOnCloseRequest((event) -> client.disconnect());
            thisStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
