package com.rprtr258;

import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.*;

import static java.lang.Math.*;

public class Main extends Application {
    private final long[] lastNanoTime = {System.nanoTime()};
    private Tank tank;
    private ArrayList<String> input = new ArrayList<>();
    private List<Renderable> renderList = new ArrayList<>();
    private List<Entity> updateList = new ArrayList<>();
    private Queue<Entity> deleteQueue = new ArrayDeque<>();

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
        Earth earth = new Earth();

        renderList.add(gui);
        renderList.add(earth);
        renderList.add(tank);

        updateList.add(tank);

        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                handleInput();

                update(currentNanoTime);

                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                render(gc);
                gc.setFill(Color.rgb(56, 35, 40));
                gc.fillRect(0, 50, 21, 480);
                gc.fillRect(621, 0, 20, 480);
                gc.fillRect(0, 456, 640, 25);

                gc.setStroke(Color.rgb(249, 89, 247));
                gc.setLineWidth(2);
                gc.strokeRect(16, 87, 610, 374);
                gc.strokeRect(20, 91, 602, 366);
            }
        }.start();
        theStage.show();
    }

    private void update(long currentNanoTime) {
        double elapsedTime = (currentNanoTime - lastNanoTime[0]) / 1e9;
        lastNanoTime[0] = currentNanoTime;

        //if (tank.getBoundary().getMaxY() >= 380)
        //    tank.addVelocity(new Point2D(0, -100));
        for (Entity e : updateList) {
            e.update(elapsedTime);
            if (e.isReadyToDie())
                deleteQueue.add(e);
        }
        while (!deleteQueue.isEmpty()) {
            renderList.remove(deleteQueue.peek());
            updateList.remove(deleteQueue.remove());
        }
    }

    private void handleInput() {
        if (input.contains("LEFT"))
            tank.addVelocity(new Point2D(-50,0));
        if (input.contains("RIGHT"))
            tank.addVelocity(new Point2D(50,0));
        if (input.contains("UP"))
            tank.increaseAngle();
        if (input.contains("DOWN"))
            tank.decreaseAngle();
        if (input.contains("SPACE")) {
            Bullet bullet = new Bullet(tank.getPosition(), new Point2D(cos(tank.getAngle().getValue()), -sin(tank.getAngle().getValue())));
            renderList.add(bullet);
            updateList.add(bullet);
        }
    }

    private void render(GraphicsContext gc) {
        for (Renderable e : renderList)
            e.render(gc);
    }
}