package com.rprtr258.client;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Class representing bullet produced by Tank.
 */
public class Bullet extends Entity {
    private Point2D direction;
    private double size;
    private final Earth earthRef;

    public Bullet(Point2D pos, Point2D dir, Earth earth, double size) {
        super(pos.add(0, -1));
        this.size = size;
        earthRef = earth;
        direction = dir.normalize();
        addAcceleration(gravityAcc);
    }

    /**
     * Renders bullet.
     * @param gc graphics context of window
     */
    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillOval(position.getX() - size, position.getY() - size, size * 2, size * 2);
    }

    /**
     * Updates bullet position.
     * @param time world time
     */
    @Override
    public void update(double time) {
        super.update(time);
        addVelocity(direction.multiply(100));
        if (position.getX() < 10 || position.getY() < 90 ||
            position.getX() > 650 || position.getY() > 500 ||
            earthRef.checkCollision(position))
            readyToDie = true;
    }
}
