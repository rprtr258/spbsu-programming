package com.rprtr258;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Class which launches JavaFX application.
 */
public class Main extends Application {
    /**
     * Launches JavaFX application.
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Starts main stage. Required by JavaFX.
     * @param primaryStage stage that will be started.
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("Spectre slider manipulator");
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
