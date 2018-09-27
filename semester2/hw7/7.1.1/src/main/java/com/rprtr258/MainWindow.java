package com.rprtr258;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main application class.
 */
public class MainWindow extends Application {
    /**
     * Entry point.
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Launches stage.
     * @param primaryStage given stage.
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
            Parent root = fxmlLoader.load();
            Controller myController = fxmlLoader.getController();
            myController.setStage(primaryStage);
            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("Java .class reverser");
            primaryStage.setMinWidth(600);
            primaryStage.setMinHeight(400);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
