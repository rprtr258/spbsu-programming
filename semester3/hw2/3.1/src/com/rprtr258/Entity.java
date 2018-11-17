package com.rprtr258;

import javafx.geometry.Point2D;

public abstract class Entity implements Renderable {
    protected static Point2D gravityAcc = Point2D.ZERO.add(0, 30);
    protected Point2D position;
    protected Point2D velocity = Point2D.ZERO;
    protected Point2D acceleration = Point2D.ZERO;
    protected Point2D permanentVelocity = Point2D.ZERO;
    protected boolean readyToDie = false;

    public Entity(Point2D pos) {
        position = pos;
    }

    public Entity(double x, double y) {
        this(new Point2D(x, y));
    }

    public void addVelocity(Point2D delta) {
        velocity = velocity.add(delta);
    }

    public void addAcceleration(Point2D acc) {
        acceleration = acceleration.add(acc);
    }

    public void update(double time) {
        permanentVelocity = permanentVelocity.add(acceleration.multiply(time));
        velocity = velocity.add(permanentVelocity).multiply(time);
        position = position.add(velocity);
        velocity = Point2D.ZERO;
    }

    public Point2D getPosition() {
        return position;
    }

    public boolean isReadyToDie() {
        return readyToDie;
    }
}