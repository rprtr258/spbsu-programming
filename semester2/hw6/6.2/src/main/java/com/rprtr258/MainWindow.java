package com.rprtr258;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainWindow extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("Tic Tac Toe");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
