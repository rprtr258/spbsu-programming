package com.rprtr258.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ConnectionWindow extends Application {
    public static void main(String[] args) {
        launch(args);
    }

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
