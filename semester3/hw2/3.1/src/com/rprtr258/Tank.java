package com.rprtr258;

import javafx.geometry.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

import static java.lang.Math.*;

public class Tank extends Entity {
    private DoubleProperty angle = new DoubleProperty();
    private double angleDelta = 0;

    public Tank(double x, double y) {
        super(x, y);
        addAcceleration(new Point2D(0, 120));
    }

    public void render(GraphicsContext gc) {
        double radius = 20.0;
        Point2D gunEndPoint = position.add(Point2D.ZERO.add(cos(angle.getValue()), -sin(angle.getValue())).multiply(radius + 2));
        gc.setStroke(Color.rgb(0, 255, 0));
        gc.setLineWidth(1);
        gc.strokeLine(position.getX(), position.getY(), gunEndPoint.getX(), gunEndPoint.getY());

        gc.setFill(Color.rgb(0, 255, 0));
        gc.fillArc(position.getX() - radius / 2, position.getY() - radius / 2 + 3, radius, radius, 0, 180, ArcType.ROUND);
    }

    public void increaseAngle() {
        angleDelta += 1;
    }

    public void decreaseAngle() {
        angleDelta -= 1;
    }

    public DoubleProperty getAngle() {
        return angle;
    }

    @Override
    public void update(double time) {
        super.update(time);
        if (position.getY() > 380)
            position = position.add(0, 380 - position.getY());
        if (position.getX() < 22)
            position = position.add(22 - position.getX(), 0);
        if (position.getX() > 620)
            position = position.add(620 - position.getX(), 0);
        if (angleDelta < 0)
            angle.setValue(max(angle.getValue() + angleDelta * time, 0));
        else
            angle.setValue(min(angle.getValue() + angleDelta * time, PI));
        angleDelta = 0;
    }
}
