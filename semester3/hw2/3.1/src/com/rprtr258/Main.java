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

public class Main extends Application {
    private final long[] lastNanoTime = {System.nanoTime()};
    private Tank tank = new Tank(200, 100);
    private GraphicsContext gc;
    private Font theFont = Font.font("Helvetica", FontWeight.BOLD, 20);
    private ArrayList<String> input = new ArrayList<>();
    private Canvas canvas;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage theStage) {
        theStage.setTitle("Pooshka");

        Group root = new Group();
        Scene theScene = new Scene(root);
        theStage.setScene(theScene);

        canvas = new Canvas(640, 480);
        root.getChildren().add(canvas);

        theScene.setOnKeyPressed(event -> {
            String code = event.getCode().toString();
            if (!input.contains(code))
                input.add(code);
        });

        theScene.setOnKeyReleased(event -> {
            String code = event.getCode().toString();
            input.remove(code);
        });
        gc = canvas.getGraphicsContext2D();

        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                update(currentNanoTime);
            }
        }.start();
        theStage.show();
    }

    private void update(long currentNanoTime) {
        double elapsedTime = (currentNanoTime - lastNanoTime[0]) / 1e9;
        lastNanoTime[0] = currentNanoTime;

        handleInput();

        tank.update(elapsedTime);

        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        render(gc);
    }

    private void handleInput() {
        if (input.contains("LEFT"))
            tank.addVelocity(-50,0);
        if (input.contains("RIGHT"))
            tank.addVelocity(50,0);
        if (input.contains("UP"))
            tank.increaseAngle();
        if (input.contains("DOWN"))
            tank.decreaseAngle();
        if (tank.getBoundary().getMaxY() <= 380)
            tank.addVelocity(0, 100);
    }

    private void render(GraphicsContext gc) {
        gc.setFill(Color.rgb(56, 35, 40));
        gc.fillRect(0, 0, 640, 480);

        gc.setStroke(Color.rgb(249, 89, 247));
        gc.setLineWidth(2);
        gc.strokeRect(16, 87, 610, 374);
        gc.strokeRect(20, 91, 602, 366);

        gc.setFont(theFont);

        gc.setFill(Color.rgb(170, 0, 170));
        String angleText = "Angle: ";
        gc.fillText(angleText, 15, 23);

        gc.setFill(Color.rgb(255, 255, 83));
        String angleValueText = String.format("%.0f", (180 * tank.getAngle() / Math.PI));
        gc.fillText(angleValueText, 80, 23);

        tank.render(gc);
    }
}