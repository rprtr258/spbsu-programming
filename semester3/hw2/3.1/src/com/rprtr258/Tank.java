package com.rprtr258;

import javafx.geometry.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

import java.util.List;

import static java.lang.Math.*;

public class Tank extends Entity {
    private DoubleProperty angle = new DoubleProperty();
    private Point2D dir = Point2D.ZERO.add(0, 1);
    private double angleDelta = 0;
    private Earth earthRef;

    public Tank(double x, double y, Earth earth) {
        super(x, y);
        earthRef = earth;
        angle.setValue(toRadians(28));
    }

    public void render(GraphicsContext gc) {
        double radius = 20.0;
        Point2D gunEndPoint = position.add(Point2D.ZERO.add(cos(angle.getValue()), -sin(angle.getValue())).multiply(radius + 2));
        gc.setStroke(Color.rgb(0, 255, 0));
        gc.setLineWidth(1);
        gc.strokeLine(position.getX(), position.getY(), gunEndPoint.getX(), gunEndPoint.getY());

        gc.setFill(Color.rgb(0, 255, 0));
        double ang = atan2(-dir.getX(), dir.getY());
        Point2D center = position.subtract(radius / 2, radius / 2);
        gc.fillArc(center.getX(), center.getY(), radius, radius, -toDegrees(ang), 180, ArcType.ROUND);

        gc.setFill(Color.rgb(255, 0, 0));
        gc.fillOval(position.getX() - 3, position.getY() - 3, 6, 6);

        gc.setStroke(Color.RED);
        Point2D rstart = center.add(dir.multiply(10));
        gc.strokeLine(position.getX(), position.getY(), rstart.getX(), rstart.getY());
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
        if (position.getX() < 22)
            position = position.add(22 - position.getX(), 0);
        if (position.getX() > 620)
            position = position.add(620 - position.getX(), 0);
        fixPosition();
        if (angleDelta < 0)
            angle.setValue(max(angle.getValue() + angleDelta * time, 0));
        else
            angle.setValue(min(angle.getValue() + angleDelta * time, PI));
        angleDelta = 0;
    }
    private void fixPosition() {
        List<Point2D> res = earthRef.getIntersection(position, dir);
        position = res.get(0);
        dir = res.get(1);
    }
}
