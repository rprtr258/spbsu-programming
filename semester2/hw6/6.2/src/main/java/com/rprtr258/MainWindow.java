package com.rprtr258;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main class.
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
     * JavaFX application loader method.
     * @param primaryStage stage that will be used.
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("Tic Tac Toe");
            primaryStage.setMinHeight(200);
            primaryStage.setMinWidth(200);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
