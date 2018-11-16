package com.rprtr258;

import javafx.geometry.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import static java.lang.Math.*;

public class Tank extends Entity {
    private double angle = 0;
    private double angleDelta = 0;

    public Tank(double x, double y) {
        super(x, y);
    }

    public void render(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.setStroke(Color.BLACK);
        Point2D center = position;
        double radius = 30.0;

        Point2D gunEndPoint = position.add(Point2D.ZERO.add(cos(angle), -sin(angle)).multiply(50));
        gc.strokeLine(position.getX(), position.getY(), gunEndPoint.getX(), gunEndPoint.getY());
        gc.fillOval(center.getX() - radius / 2, center.getY() - radius / 2, radius, radius);
    }

    public Rectangle2D getBoundary() {
        return new Rectangle2D(position.getX() - 10, position.getY() - 10, 20, 20);
    }

    public void increaseAngle() {
        angleDelta += 1;
    }

    public void decreaseAngle() {
        angleDelta -= 1;
    }

    public double getAngle() {
        return angle;
    }

    public void update(double time) {
        super.update(time);
        if (angleDelta < 0)
            angle = max(angle + angleDelta * time, 0);
        else
            angle = min(angle + angleDelta * time, PI);
        angleDelta = 0;
    }
}
