package com.rprtr258.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Class that launches client connection window which used to
 * setup connection settings to server such as host and port.
 */
public class ConnectionWindow extends Application {
    /**
     * Entry point, launches JavaFX app.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Window launch method required by JavaFX framework.
     *
     * @param primaryStage stage to show.
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("ConnectionWindow.fxml"));
            primaryStage.setScene(new Scene(parent));
            primaryStage.setTitle("Connect server");
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
