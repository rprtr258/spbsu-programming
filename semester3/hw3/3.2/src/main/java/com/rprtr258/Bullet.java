package com.rprtr258;

import javafx.geometry.Point2D;
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

    /**
     * Bullet class constructor
     * @param pos bullet position on screen
     * @param dir bullet movement direction
     * @param earth earth reference
     * @param size bullet size
     */
    public Bullet(Point2D pos, Point2D dir, Earth earth, double size) {
        super(pos.add(0, -1));
        this.size = size;
        earthRef = earth;
        direction = dir.normalize();
        addAcceleration(gravityAcc.multiply(size / 4));
    }

    /**
     * Checks if bullet hits tank
     * @param tank tank to check
     * @return true if hits
     */
    public boolean hits(Tank tank) {
        if (!isExploded)
            return false;
        return tank.getPosition().distance(position) <= size * 100 / 20;
    }

    /**
     * Renders bullet.
     * @param gc graphics context of window
     */
    @Override
    public void render(GraphicsAdapter gc) {
        if (isExploded) {
            gc.setFill(Color.YELLOW);
            double radius = size * explosionTime / 20;
            gc.fillCircle(position.getX(), position.getY(), radius);
        } else {
            gc.setFill(Color.BLACK);
            gc.fillCircle(position.getX(), position.getY(), size);
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
