package com.rprtr258;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Bullet extends Entity {
    private Point2D direction;

    public Bullet(Point2D pos, Point2D dir) {
        super(pos);
        direction = dir.normalize();
        addAcceleration(gravityAcc);
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillOval(position.getX() - 1, position.getY() - 1, 2, 2);
    }

    @Override
    public void update(double time) {
        addVelocity(direction.multiply(100));
        super.update(time);
        if (position.getX() < 10 || position.getY() < 80 ||
            position.getX() > 650 || position.getY() > 500)
            readyToDie = true;
    }
}
