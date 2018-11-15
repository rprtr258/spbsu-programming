package com.rprtr258;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.animation.AnimationTimer;
import javafx.stage.Stage;

import java.util.ArrayList;
import static java.lang.Math.*;

public class Example5 extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage theStage) {
        theStage.setTitle("Pooshka");

        Group root = new Group();
        Scene theScene = new Scene(root);
        theStage.setScene(theScene);

        Canvas canvas = new Canvas(640, 480);
        root.getChildren().add(canvas);

        ArrayList<String> input = new ArrayList<>();

        theScene.setOnKeyPressed(event -> {
            String code = event.getCode().toString();
            if (!input.contains(code))
                input.add(code);
        });

        theScene.setOnKeyReleased(event -> {
            String code = event.getCode().toString();
            input.remove(code);
        });

        GraphicsContext gc = canvas.getGraphicsContext2D();

        Font theFont = Font.font("Helvetica", FontWeight.BOLD, 24);
        gc.setFont(theFont);
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);
        
        Sprite tank = new Sprite();
        tank.setImage("briefcase.png");
        tank.setPosition(200, 0);

        final long[] lastNanoTime = {System.nanoTime()};

        final Double[] angle = {0.0};

        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                double elapsedTime = (currentNanoTime - lastNanoTime[0]) / 1000000000.0;
                lastNanoTime[0] = currentNanoTime;
                
                tank.setVelocity(0,0);
                if (input.contains("LEFT"))
                    tank.addVelocity(-50,0);
                if (input.contains("RIGHT"))
                    tank.addVelocity(50,0);
                if (input.contains("UP"))
                    angle[0] = min(PI, angle[0] + 0.01);
                if (input.contains("DOWN"))
                    angle[0] = max(0, angle[0] - 0.01);
                if (tank.getBoundary().getMaxY() <= 380)
                    tank.addVelocity(0, 100);

                tank.update(elapsedTime);
                
                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                tank.render(gc);
                String pointsText = String.format("Angle: %.2f", (180 * angle[0] / Math.PI));
                gc.fillText(pointsText, 360, 36);
                gc.strokeText(pointsText, 360, 36);

                gc.strokeLine(tank.getBoundary().getMinX(), tank.getBoundary().getMinY(), tank.getBoundary().getMinX() + 100 * cos(angle[0]), tank.getBoundary().getMinY() - 100 * sin(angle[0]));
            }
        }.start();
        theStage.show();
    }
}