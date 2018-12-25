package com.rprtr258.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.*;

import static java.lang.Math.*;

/**
 * Main class
 */
public class Main extends Application {
    private final long[] lastNanoTime = {System.nanoTime()};
    private Tank tank;
    private ArrayList<String> input = new ArrayList<>();
    private List<Renderable> renderList = new ArrayList<>();
    private List<Entity> updateList = new ArrayList<>();
    private Queue<Entity> deleteQueue = new ArrayDeque<>();
    private int reload = 0;
    private static String color = "#00FF00";
    private static String id = "";
    private static SocketChannel socketChannel = null;

    public static void main(String[] args) {
        Arrays.stream(args).forEach(System.out::println);
        try {
            socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress("127.0.0.1", 1337));
            ByteBuffer buf = ByteBuffer.allocate(256);
            while (socketChannel.read(buf) == -1);
            color = new String(buf.array()).trim().substring(0, buf.position());
            System.out.println(color);
            buf.clear();
            while (socketChannel.read(buf) == -1);
            id = new String(buf.array()).trim().substring(0, buf.position());
            System.out.println(id);
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
            if (!input.contains(code))
                input.add(code);
            try {
                if ("LEFT".equals(code))
                    socketChannel.write(ByteBuffer.wrap((id + "goes left").getBytes()));
                if ("RIGHT".equals(code))
                    socketChannel.write(ByteBuffer.wrap((id + "goes right").getBytes()));
                if ("UP".equals(code))
                    socketChannel.write(ByteBuffer.wrap((id + "goes up").getBytes()));
                if ("DOWN".equals(code))
                    socketChannel.write(ByteBuffer.wrap((id + "goes down").getBytes()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        theScene.setOnKeyReleased(event -> {
            String code = event.getCode().toString();
            input.remove(code);
            try {
                if ("LEFT".equals(code))
                    socketChannel.write(ByteBuffer.wrap((id + " stop going left").getBytes()));
                if ("RIGHT".equals(code))
                    socketChannel.write(ByteBuffer.wrap((id + " stop going left").getBytes()));
                if ("UP".equals(code))
                    socketChannel.write(ByteBuffer.wrap((id + " stop going left").getBytes()));
                if ("DOWN".equals(code))
                    socketChannel.write(ByteBuffer.wrap((id + " stop going left").getBytes()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        GraphicsContext gc = canvas.getGraphicsContext2D();

        Earth earth = new Earth();
        tank = new Tank(200, 100, color, earth);
        GUI gui = new GUI(tank.getAngle());

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
                gc.fillRect(0, 87, 640, 5);

                gc.setStroke(Color.rgb(249, 89, 247));
                gc.setLineWidth(2);
                gc.strokeRect(16, 87, 610, 374);
                gc.strokeRect(20, 91, 602, 366);
            }
        }.start();
        theStage.show();
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