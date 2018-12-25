package com.rprtr258.client;

import javafx.application.*;
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

/**
 * Main class
 */
public class Main extends Application {
    private final long[] lastNanoTime = {System.nanoTime()};
    private Tank tank;
    private List<String> input = new ArrayList<>();
    private List<Renderable> renderList = new ArrayList<>();
    private List<Entity> updateList = new ArrayList<>();
    private Queue<Entity> deleteQueue = new ArrayDeque<>();
    private int reload = 0;
    private static Scanner in = null;
    private static PrintWriter out = null;
    private static InputStream is = null;

    public static void main(String[] args) {
        Arrays.stream(args).forEach(System.out::println);
        try {
            Socket socketChannel = new Socket();
            socketChannel.connect(new InetSocketAddress("127.0.0.1", 1337));
            in = new Scanner(socketChannel.getInputStream());
            is = socketChannel.getInputStream();
            out = new PrintWriter(socketChannel.getOutputStream());
        } catch (IOException e) {
            System.err.println("Error occurred during connection:");
            e.printStackTrace();
            Platform.exit();
        }
        launch(args);
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
                out.write("go " + code + "\n");
                out.flush();
                input.add(code);
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
        String color = "#00FF00";
        tank = new Tank(600, 100, color, earth);
        GUI gui = new GUI(tank.getAngle());

        renderList.add(gui);
        renderList.add(earth);
        renderList.add(tank);

        updateList.add(tank);

        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                handleInput();
                processOpponentActions();

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
                System.out.println(in.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates game state
     * @param currentNanoTime time passed since beginning of time in milliseconds
     */
    private void update(long currentNanoTime) {
        reload = max(reload - 1, 0);
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