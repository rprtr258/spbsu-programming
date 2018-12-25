package com.rprtr258;

import javafx.geometry.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

import java.util.List;

import static java.lang.Math.*;

/**
 *  Tank entity that is controlled by the player.
 */
public class Tank extends Entity {
    private double angle;
    private Point2D dir = new Point2D(0, 1);
    private double angleDelta = 0;
    private final Earth earthRef;
    private final String color;
    private int bulletSize = 2;
    private int reload = 0;

    public Tank(Point2D pos, String color, Earth earth) {
        super(pos);
        earthRef = earth;
        angle = toRadians(28);
        this.color = color;
    }

    public void handleInput(List<String> input, List<Bullet> bulletsList) {
        if (input.contains("LEFT"))
            goLeft();
        if (input.contains("RIGHT"))
            goRight();
        if (input.contains("UP"))
            increaseAngle();
        if (input.contains("DOWN"))
            decreaseAngle();
        if (input.contains("ENTER") && reload == 0) {
            reload = 100;
            Bullet bullet = new Bullet(getPosition(), new Point2D(cos(getAngle()), -sin(getAngle())), earthRef, bulletSize);
            bulletsList.add(bullet);
        }
        if (input.contains("C")) {
            input.remove("C");
            bulletSize = bulletSize % 15 + 1;
        }
    }
    
    public int getBulletSize() {
        return bulletSize;
    }

    /**
     * Renders tank and gun. In debug mode also shows trajectory and
     * surface normal line.
     * @param gc graphics context of window
     */
    @Override
    public void render(GraphicsContext gc) {
        double radius = 20.0;
        Point2D gunEndPoint = position.add(new Point2D(cos(angle), -sin(angle)).multiply(radius + 2));
        Point2D gunStartPoint = position.add(new Point2D(cos(angle), -sin(angle)).multiply(2));
        gc.setStroke(Color.rgb(0, 255, 0));
        gc.setLineWidth(2);
        gc.strokeLine(gunStartPoint.getX(), gunStartPoint.getY(), gunEndPoint.getX(), gunEndPoint.getY());

        double ang = atan2(-dir.getX(), dir.getY());
        Point2D center = position.subtract(radius / 2, radius / 2);
        gc.setFill(Color.web(color));
        gc.fillArc(center.getX(), center.getY(), radius, radius, -toDegrees(ang), 180, ArcType.ROUND);
    }

    /**
     * @return gun's angle
     */
    public double getAngle() {
        return angle;
    }

    /**
     * Updates tank position and gun's angle.
     * @param time world time
     */
    @Override
    public void update(double time) {
        super.update(time);
        reload = max(reload - 1, 0);
        if (position.getX() < 22)
            position = position.add(22 - position.getX(), 0);
        if (position.getX() > 620)
            position = position.add(620 - position.getX(), 0);
        fixPosition();
        angle += angleDelta;
        while (angle >= 2 * PI)
            angle -= 2 * PI;
        while (angle < 0)
            angle += 2 * PI;
        angleDelta = 0;
    }

    /**
     * Makes tank going left.
     */
    private void goLeft() {
        double a = atan2(dir.getX(), dir.getY());
        Point2D deltaVelocity = new Point2D(-50, 0);
        deltaVelocity = new Point2D(cos(a) * deltaVelocity.getX() + sin(a) * deltaVelocity.getY(), -sin(a) * deltaVelocity.getX() + cos(a) * deltaVelocity.getY());
        addVelocity(deltaVelocity);
    }

    /**
     * Makes tank going right.
     */
    private void goRight() {
        double a = atan2(dir.getX(), dir.getY());
        Point2D deltaVelocity = new Point2D(50, 0);
        deltaVelocity = new Point2D(cos(a) * deltaVelocity.getX() + sin(a) * deltaVelocity.getY(), -sin(a) * deltaVelocity.getX() + cos(a) * deltaVelocity.getY());
        addVelocity(deltaVelocity);
    }

    /**
     * Increases angle of gun in counter-clockwise direction.
     */
    private void increaseAngle() {
        angleDelta += 0.01;
    }

    /**
     * Increases angle of gun in clockwise direction.
     * Same as decreasing in counter-clockwise direction.
     */
    private void decreaseAngle() {
        angleDelta -= 0.01;
    }

    /**
     * Fixes tank position and angle of tank according to surface.
     */
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
