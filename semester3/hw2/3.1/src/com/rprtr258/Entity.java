package com.rprtr258;

import javafx.geometry.Point2D;

public abstract class Entity implements Renderable {
    protected Point2D position;
    protected Point2D velocity = Point2D.ZERO;
    protected boolean readyToDie = false;

    public Entity(double x, double y) {
        setPosition(x, y);
    }

    public Entity(Point2D pos) {
        this(pos.getX(), pos.getY());
    }

    private void setPosition(double x, double y) {
        position = Point2D.ZERO.add(x, y);
    }

    public void addVelocity(double x, double y) {
        velocity = velocity.add(x, y);
    }

    public void addVelocity(Point2D delta) {
        velocity = velocity.add(delta);
    }

    public void update(double time) {
        position = position.add(velocity.multiply(time));
        velocity = Point2D.ZERO;
    }

    public Point2D getPosition() {
        return position;
    }

    public boolean isReadyToDie() {
        return readyToDie;
    }

    @Override
    public String toString() {
        return String.format("Position: %s\nVelocity: %s\n", position.toString(), velocity.toString());
    }
}