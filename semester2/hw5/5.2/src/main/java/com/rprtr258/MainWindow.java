package com.rprtr258;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main class. Launches main window.
 */
public class MainWindow extends Application {
    /**
     * Main function, launches JavaFX application.
     * @param args command line arguments which are ignored.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Required by JavaFX framework. Sets window and starts JavaFX application.
     * @param primaryStage stage that will be used.
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("Simple calculator");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
