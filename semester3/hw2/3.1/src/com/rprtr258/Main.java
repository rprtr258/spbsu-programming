package com.rprtr258;

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.animation.AnimationTimer;
import javafx.stage.Stage;

import java.util.*;

public class Main extends Application {
    private final long[] lastNanoTime = {System.nanoTime()};
    private Tank tank;
    private ArrayList<String> input = new ArrayList<>();
    private List<Renderable> entityList = new ArrayList<>();

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

        tank = new Tank(200, 100);
        GUI gui = new GUI(tank.getAngle());

        entityList.add(gui);
        entityList.add(tank);

        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                handleInput();

                update(currentNanoTime);

                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                render(gc);
            }
        }.start();
        theStage.show();
    }

    private void update(long currentNanoTime) {
        double elapsedTime = (currentNanoTime - lastNanoTime[0]) / 1e9;
        lastNanoTime[0] = currentNanoTime;

        if (tank.getBoundary().getMaxY() <= 380)
            tank.addVelocity(0, 100);
        tank.update(elapsedTime);
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
    }

    private void render(GraphicsContext gc) {
        for (Renderable e : entityList)
            e.render(gc);
    }
}