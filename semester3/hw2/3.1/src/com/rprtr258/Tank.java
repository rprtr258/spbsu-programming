package com.rprtr258;

import javafx.geometry.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import static java.lang.Math.*;

public class Tank extends Entity {
    private DoubleProperty angle = new DoubleProperty();
    private double angleDelta = 0;

    public Tank(double x, double y) {
        super(x, y);
        addAcceleration(new Point2D(0, 100));
    }

    public void render(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.setStroke(Color.BLACK);
        Point2D center = position;
        double radius = 30.0;

        Point2D gunEndPoint = position.add(Point2D.ZERO.add(cos(angle.getValue()), -sin(angle.getValue())).multiply(50));
        gc.strokeLine(position.getX(), position.getY(), gunEndPoint.getX(), gunEndPoint.getY());
        gc.fillOval(center.getX() - radius / 2, center.getY() - radius / 2, radius, radius);
    }

    public Rectangle2D getBoundary() {
        return new Rectangle2D(position.getX() - 10, position.getY() - 10, 20, 20);
    }

    public void increaseAngle() {
        angleDelta += 3;
    }

    public void decreaseAngle() {
        angleDelta -= 3;
    }

    public DoubleProperty getAngle() {
        return angle;
    }

    @Override
    public void update(double time) {
        super.update(time);
        if (position.getY() > 380) {
            position = position.add(0, 380 - position.getY());
        }
        if (angleDelta < 0)
            angle.setValue(max(angle.getValue() + angleDelta * time, 0));
        else
            angle.setValue(min(angle.getValue() + angleDelta * time, PI));
        angleDelta = 0;
    }
}
