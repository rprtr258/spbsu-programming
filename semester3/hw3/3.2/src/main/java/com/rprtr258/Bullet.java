package com.rprtr258;

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
    private boolean isExploded = false;
    private int explosionTime = -1;

    public Bullet(Point2D pos, Point2D dir, Earth earth, double size) {
        super(pos.add(0, -1));
        this.size = size;
        earthRef = earth;
        direction = dir.normalize();
        addAcceleration(gravityAcc.multiply(size / 4));
    }

    public boolean hits(Tank tank) {
        if (!isExploded)
            return false;
        return tank.getPosition().distance(position) <= size * explosionTime / 20;
    }

    /**
     * Renders bullet.
     * @param gc graphics context of window
     */
    @Override
    public void render(GraphicsContext gc) {
        if (isExploded) {
            gc.setFill(Color.YELLOW);
            double radius = size * explosionTime / 20;
            gc.fillOval(position.getX() - radius, position.getY() - radius, 2 * radius,  2 * radius);
        } else {
            gc.setFill(Color.BLACK);
            gc.fillOval(position.getX() - size, position.getY() - size, size * 2, size * 2);
        }
    }

    /**
     * Updates bullet position.
     * @param time world time
     */
    @Override
    public void update(double time) {
        if (isExploded) {
            explosionTime--;
            if (explosionTime == 0)
                readyToDie = true;
        } else {
            super.update(time);
            addVelocity(direction.multiply(100));
            if (earthRef.checkCollision(position)) {
                isExploded = true;
                explosionTime = 100;
            } else if (position.getX() < 10 || position.getY() < 90 ||
                     position.getX() > 650 || position.getY() > 500)
                readyToDie = true;
        }
    }
}
