package com.rprtr258;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public class Bullet extends Entity {
    private Point2D direction;

    public Bullet(Point2D pos, Point2D dir) {
        super(pos);
        direction = dir.normalize();
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.fillOval(position.getX() - 5, position.getY() - 5, 10, 10);
    }

    @Override
    public void update(double time) {
        double speed = time * 10000;
        addVelocity(direction.multiply(speed));
        super.update(time);
        if (position.getX() < 0 || position.getX() > 500)
            readyToDie = true;
    }
}
