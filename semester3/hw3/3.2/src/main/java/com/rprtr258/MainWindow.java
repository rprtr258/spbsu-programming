package com.rprtr258;

import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;
import java.net.*;
import java.util.*;

import static java.lang.Math.*;
import com.rprtr258.client.*;

public class MainWindow extends Application {
    private final long[] lastNanoTime = {System.nanoTime()};
    private Tank tank;
    private Tank opponentTank;
    private List<String> input = new ArrayList<>();
    private List<String> opponentInput = new ArrayList<>();
    private List<Renderable> renderList = new ArrayList<>();
    private List<Entity> updateList = new ArrayList<>();
    private Queue<Entity> deleteQueue = new ArrayDeque<>();
    private int reload = 0;
    private int opponentReload = 0;
    private static Scanner in = null;
    private static PrintWriter out = null;
    private static InputStream is = null;
    private static Point2D myStart = null;
    private static Point2D opponentStart = null;

    public static void setSocket(Socket socket) {
        try {
            is = socket.getInputStream();
            in = new Scanner(is);
            out = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setCoords(Point2D myCoord, Point2D opponentCoord) {
        myStart = myCoord;
        opponentStart = opponentCoord;
    }

    /**
     * Starts application
     * @param theStage given stage
     */
    @Override
    public void start(Stage theStage) {
        theStage.setTitle("Pooshka++");

        Group root = new Group();
        Scene theScene = new Scene(root);
        theStage.setScene(theScene);

        Canvas canvas = new Canvas(640, 480);
        root.getChildren().add(canvas);

        theScene.setOnKeyPressed(event -> {
            String code = event.getCode().toString();
            if (!input.contains(code)) {
                input.add(code);
                out.write("go " + code + "\n");
                out.flush();
            }
        });
        theScene.setOnKeyReleased(event -> {
            String code = event.getCode().toString();
            out.write("stop " + code + "\n");
            out.flush();
            input.remove(code);
        });

        GraphicsContext gc = canvas.getGraphicsContext2D();

        Earth earth = new Earth();
        tank = new Tank(myStart, "#00FF00", earth);
        opponentTank = new Tank(opponentStart, "#FF0000", earth);
        GUI gui = new GUI(tank.getAngle());

        renderList.add(gui);
        renderList.add(earth);
        renderList.add(tank);
        renderList.add(opponentTank);

        updateList.add(tank);
        updateList.add(opponentTank);

        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                processOpponentActions();
                handleInput();

                update(currentNanoTime);

                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                render(gc);
                gc.setFill(Color.rgb(56, 35, 40));
                gc.fillRect(0, 50, 21, 480);
                gc.fillRect(621, 0, 20, 480);
                gc.fillRect(0, 456, 640, 25);
                gc.fillRect(0, 87, 640, 5);

                gc.setStroke(Color.rgb(249, 89, 247));
                gc.setLineWidth(2);
                gc.strokeRect(16, 87, 610, 374);
                gc.strokeRect(20, 91, 602, 366);
            }
        }.start();
        theStage.show();
    }

    private void processOpponentActions() {
        try {
            while (is.available() > 0) {
                String event = in.nextLine();
                String[] tokens = event.split(" ");
                String pressing = tokens[0];
                String key = tokens[1];
                if ("go".equals(pressing)) {
                    opponentInput.add(key);
                } else if ("stop".equals(pressing)) {
                    opponentInput.remove(key);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles user input
     */
    private void handleInput() {
        if (input.contains("LEFT"))
            tank.goLeft();
        if (input.contains("RIGHT"))
            tank.goRight();
        if (input.contains("UP"))
            tank.increaseAngle();
        if (input.contains("DOWN"))
            tank.decreaseAngle();
        if (input.contains("ENTER")) {
            if (reload == 0) {
                reload = 100;
                Bullet bullet = new Bullet(tank.getPosition(), new Point2D(cos(tank.getAngle().getValue()), -sin(tank.getAngle().getValue())));
                renderList.add(bullet);
                updateList.add(bullet);
            }
        }
        if (opponentInput.contains("LEFT"))
            opponentTank.goLeft();
        if (opponentInput.contains("RIGHT"))
            opponentTank.goRight();
        if (opponentInput.contains("UP"))
            opponentTank.increaseAngle();
        if (opponentInput.contains("DOWN"))
            opponentTank.decreaseAngle();
        if (opponentInput.contains("ENTER")) {
            if (opponentReload == 0) {
                opponentReload = 100;
                Bullet bullet = new Bullet(opponentTank.getPosition(), new Point2D(cos(opponentTank.getAngle().getValue()), -sin(opponentTank.getAngle().getValue())));
                renderList.add(bullet);
                updateList.add(bullet);
            }
        }
    }

    /**
     * Updates game state
     * @param currentNanoTime time passed since beginning of time in milliseconds
     */
    private void update(long currentNanoTime) {
        reload = max(reload - 1, 0);
        opponentReload = max(opponentReload - 1, 0);
        double elapsedTime = (currentNanoTime - lastNanoTime[0]) / 1e9;
        lastNanoTime[0] = currentNanoTime;

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

    /**
     * Renders game
     * @param gc graphics context of window
     */
    private void render(GraphicsContext gc) {
        for (Renderable e : renderList)
            e.render(gc);
    }
}

