package com.rprtr258.client;

import javafx.geometry.Point2D;

/**
 * Abstract entity that can be moved and rendered.
 */
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

    /**
     * Adds velocity allowing to move entity.
     * @param delta vector to add
     */
    public void addVelocity(Point2D delta) {
        velocity = velocity.add(delta);
    }

    /**
     * Adds acceleration which repeatedly increases velocity.
     * @param acc acceleration to add
     */
    public void addAcceleration(Point2D acc) {
        acceleration = acceleration.add(acc);
    }

    /**
     * Updates entity's position.
     * @param time world time passed
     */
    public void update(double time) {
        permanentVelocity = permanentVelocity.add(acceleration.multiply(time));
        velocity = velocity.add(permanentVelocity).multiply(time);
        position = position.add(velocity);
        velocity = Point2D.ZERO;
    }

    /**
     * @return entity position
     */
    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D p) {
        position = p;
    }

    /**
     * @return true if entity is ready to be deleted
     */
    public boolean isReadyToDie() {
        return readyToDie;
    }
}