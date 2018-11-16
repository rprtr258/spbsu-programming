package com.rprtr258;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;

public abstract class Entity implements Renderable {
    protected Point2D position;
    private Point2D velocity = Point2D.ZERO;

    public Entity(double x, double y) {
        setPosition(x, y);
    }

    private void setPosition(double x, double y) {
        position = Point2D.ZERO.add(x, y);
    }

    public void addVelocity(double x, double y) {
        velocity = velocity.add(x, y);
    }

    public void update(double time) {
        position = position.add(velocity.multiply(time));
        velocity = Point2D.ZERO;
    }

    public boolean intersects(Entity s) {
        return s.getBoundary().intersects(this.getBoundary());
    }

    public abstract Rectangle2D getBoundary();

    @Override
    public String toString() {
        return String.format("Position: %s\nVelocity: %s\n", position.toString(), velocity.toString());
    }
}