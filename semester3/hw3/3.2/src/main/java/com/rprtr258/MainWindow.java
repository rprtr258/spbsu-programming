package com.rprtr258;

import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;

import java.net.*;
import java.util.*;

// TODO: correct window resize
// TODO: make help button
// TODO: add game end
public class MainWindow extends Application {
    private final long[] lastNanoTime = {System.nanoTime()};
    private Tank tank;
    private Tank opponentTank;
    private List<String> input = new ArrayList<>();
    private List<String> opponentInput = new ArrayList<>();
    private List<Renderable> renderList = new ArrayList<>();
    private List<Entity> updateList = new ArrayList<>();
    private Queue<Entity> deleteQueue = new ArrayDeque<>();
    private static SocketAdapter socketAdapter;
    private final Font theFont = Font.font("Helvetica", FontWeight.BOLD, 20);
    private static Point2D myStart = null;
    private static Point2D opponentStart = null;

    public static void setSocket(Socket socket) {
        socketAdapter = new SocketAdapter(socket);
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
                socketAdapter.write("go " + code + "\n");
            }
        });
        theScene.setOnKeyReleased(event -> {
            String code = event.getCode().toString();
            socketAdapter.write("stop " + code + "\n");
            input.remove(code);
        });

        GraphicsContext gc = canvas.getGraphicsContext2D();

        Earth earth = new Earth();
        tank = new Tank(myStart, "#00FF00", earth);
        opponentTank = new Tank(opponentStart, "#FF0000", earth);

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
                gc.setFill(Color.rgb(56, 35, 40));
                gc.fillRect(0, 0, 640, 480);

                gc.setFont(theFont);

                gc.setFill(Color.rgb(170, 0, 170));
                String angleText = "Angle: ";
                gc.fillText(angleText, 20, 30);

                gc.setFill(Color.rgb(255, 255, 83));
                String angleValueText = String.format("%.0f", (180 * tank.getAngle() / Math.PI));
                gc.fillText(angleValueText, 85, 30);

                gc.setFill(Color.rgb(170, 0, 170));
                String powerText = "Power: ";
                gc.fillText(powerText, 20, 53);

                gc.setFill(Color.rgb(255, 255, 83));
                String powerValueText = String.format("%d", tank.getBulletSize());
                gc.fillText(powerValueText, 85, 53);
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
                
                int curBulletSize = tank.getBulletSize();
                gc.setFill(Color.rgb(255, 255, 255));
                for (int i = 1; i <= 15; i++) {
                    if (i == curBulletSize)
                        gc.setStroke(Color.rgb(255, 0, 0));
                    else
                        gc.setStroke(Color.rgb(255, 255, 255));
                    int offset = 32 * i - 12;
                    gc.strokeLine(offset, 67, offset, 85);
                    gc.strokeLine(offset, 67, offset + 30, 67);
                    gc.strokeLine(offset + 30, 67, offset + 30, 85);
                    gc.fillOval(offset + 15 - i / 2, 76 - i / 2, i, i);
                }
            }
        }.start();
        theStage.show();
    }

    private void processOpponentActions() {
        while (socketAdapter.hasNext()) {
            String event = socketAdapter.nextLine();
            String[] tokens = event.split(" ");
            String pressing = tokens[0];
            String key = tokens[1];
            if ("go".equals(pressing)) {
                opponentInput.add(key);
            } else if ("stop".equals(pressing)) {
                opponentInput.remove(key);
            }
        }
    }

    /**
     * Handles user input
     */
    private void handleInput() {
        tank.handleInput(input, renderList, updateList);
        opponentTank.handleInput(opponentInput, renderList, updateList);
    }

    /**
     * Updates game state
     * @param currentNanoTime time passed since beginning of time in milliseconds
     */
    private void update(long currentNanoTime) {
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

