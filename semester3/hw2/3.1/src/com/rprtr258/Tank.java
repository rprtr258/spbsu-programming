package com.rprtr258;

import javafx.geometry.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

import java.util.List;
import java.util.function.Function;

import static java.lang.Math.*;

public class Tank extends Entity {
    private DoubleProperty angle = new DoubleProperty();
    private Point2D dir = new Point2D(0, 1);
    private double angleDelta = 0;
    private Earth earthRef;

    public Tank(double x, double y, Earth earth) {
        super(x, y);
        earthRef = earth;
        angle.setValue(toRadians(28));
    }

    public void goLeft() {
        double a = atan2(dir.getX(), dir.getY());
        Point2D deltaVelocity = new Point2D(-50, 0);
        deltaVelocity = new Point2D(cos(a) * deltaVelocity.getX() + sin(a) * deltaVelocity.getY(), -sin(a) * deltaVelocity.getX() + cos(a) * deltaVelocity.getY());
        addVelocity(deltaVelocity);
    }

    public void goRight() {
        double a = atan2(dir.getX(), dir.getY());
        Point2D deltaVelocity = new Point2D(50, 0);
        deltaVelocity = new Point2D(cos(a) * deltaVelocity.getX() + sin(a) * deltaVelocity.getY(), -sin(a) * deltaVelocity.getX() + cos(a) * deltaVelocity.getY());
        addVelocity(deltaVelocity);
    }

    public void render(GraphicsContext gc) {
        gc.setLineWidth(1);
        gc.setStroke(Color.AQUAMARINE);
        double X0 = position.getX();
        double Y0 = position.getY();
        double alpha = angle.getValue();
        double t0 = 10.0 / 3 * sin(alpha);
        double x0 = 100 * cos(alpha) * t0;
        double y0 = -100 * sin(alpha) * t0 + 15 * t0 * t0;
        double A = -y0 / (x0 * x0);
        double B = -2 * A * (X0 + x0);
        double C = Y0 + A * X0 * (X0 + 2 * x0);
        for (int i = -999; i < 1000; i++) {
            gc.strokeLine(X0 - i, A * (X0 - i) * (X0 - i) + B * (X0 - i) + C, X0 - i + 1, A * (X0 - i + 1) * (X0 - i + 1) + B * (X0 - i + 1) + C);
        }

        double radius = 20.0;
        Point2D gunEndPoint = position.add(new Point2D(cos(angle.getValue()), -sin(angle.getValue())).multiply(radius + 2));
        gc.setStroke(Color.rgb(0, 255, 0));
        gc.setLineWidth(2);
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
        angleDelta += 0.01;
    }

    public void decreaseAngle() {
        angleDelta -= 0.01;
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
        angle.setValue(angle.getValue() + angleDelta);
        while (angle.getValue() >= 2 * PI)
            angle.setValue(angle.getValue() - 2 * PI);
        while (angle.getValue() < 0)
            angle.setValue(angle.getValue() + 2 * PI);
        angleDelta = 0;
    }

    private void fixPosition() {
        List<Point2D> res = earthRef.getIntersection(position, dir);
        position = res.get(0);
        Point2D newDir = res.get(1);
        if (newDir.angle(dir) != 0) {
            double cross = newDir.getX() * dir.getY() - newDir.getY() * dir.getX();
            if (cross < 0)
                angleDelta -= toRadians(newDir.angle(dir));
            else
                angleDelta += toRadians(newDir.angle(dir));
            dir = newDir;
        }
    }
}
