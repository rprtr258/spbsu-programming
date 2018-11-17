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
        angle.setValue(28.0 / 180 * PI);
    }

    public void render(GraphicsContext gc) {
        double radius = 20.0;
        Point2D gunEndPoint = position.add(Point2D.ZERO.add(cos(angle.getValue()), -sin(angle.getValue())).multiply(radius + 2));
        gc.setStroke(Color.rgb(0, 255, 0));
        gc.setLineWidth(1);
        gc.strokeLine(position.getX(), position.getY(), gunEndPoint.getX(), gunEndPoint.getY());

        gc.setFill(Color.rgb(0, 255, 0));
        double ang = atan2(dir.getY(), dir.getX());
        gc.fillArc(position.getX() - radius / 2, position.getY() - radius / 2, radius, radius, -toDegrees(ang), 180, ArcType.ROUND);
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
        List<Point2D> res = earthRef.getIntersection(position, Point2D.ZERO.add(0, 1));
        Point2D intersectionPoint = res.get(0);
        if (position.distance(intersectionPoint) < 1) {
            gravityAcc = Point2D.ZERO;
            permanentVelocity = Point2D.ZERO;
        }
        position = intersectionPoint;
        dir = res.get(1);
        // find norm in intersection point
        // put tank on norm
    }
}
