package com.rprtr258.client;

import com.rprtr258.network.Client;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Connection window elements controller class.
 */
public class ConnectionWindowController {
    public TextField hostTextField;
    public TextField portTextField;
    public Button connectButton;
    public Label statusLabel;

    /**
     * Elements initialize method.
     */
    public void initialize() {
        connectButton.setOnAction((actionEvent) -> onAttemptConnect());
    }

    /**
     * Called when user tries to connect the server.
     */
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

    /**
     * Attempts to connect given server and changes scene
     * if connected successfully.
     * 
     * @param host host to connect.
     * @param port port to connect.
     */
    private void tryConnect(String host, int port) {
        Client client = new Client();
        client.configure(() -> statusLabel.setText("Lost connection to server"), () -> {});
        client.tryConnectServer(host, port, (playerName) -> launchMainWindow(playerName, client), () -> {
            statusLabel.setText("Failed to connect");
            setElementsDisable(false);
        });
    }

    /**
     * Launches main game window.
     *
     * @param playerName player name given by server.
     * @param client client which is used to talk to server.
     */
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

    /**
     * Changes elements disable state.
     *
     * @param value new disable state.
     */
    private void setElementsDisable(boolean value) {
        connectButton.setDisable(value);
        hostTextField.setDisable(value);
        portTextField.setDisable(value);
    }

    /**
     * Checks whether host is correct.
     * 
     * @param host host to check.
     * @return true if host is correct.
     */
    private boolean isCorrectHost(String host) {
        return checkIpAddress(host) || "localhost".equals(host);
    }

    /**
     * Checks whether given string is ip.
     * 
     * @param ip string to check.
     * @return true if ip is correct.
     */
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

    /**
     * Checks whether port is correct.
     * 
     * @param port port to check.
     * @return true if port is correct.
     */
    private boolean isCorrectPort(int port) {
        return (0 <= port && port < 65536);
    }
}
