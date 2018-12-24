package com.rprtr258.client;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Class representing bullet produced by Tank.
 */
public class Bullet extends Entity {
    private Point2D direction;

    public Bullet(Point2D pos, Point2D dir) {
        super(pos);
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
        gc.fillOval(position.getX() - 2, position.getY() - 2, 4, 4);
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
            position.getX() > 650 || position.getY() > 500)
            readyToDie = true;
    }
}
