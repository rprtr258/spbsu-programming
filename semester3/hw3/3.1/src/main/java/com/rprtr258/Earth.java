package com.rprtr258;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.*;

import static java.lang.Math.*;

/**
 * Class representing hardcoded surface.
 */
public class Earth implements Renderable {
    private List<Point2D> points = new ArrayList<>();

    public Earth() {
        points.add(new Point2D(21, 217));
        points.add(new Point2D(151, 217));
        points.add(new Point2D(293, 315));
        points.add(new Point2D(317, 386));
        points.add(new Point2D(389, 444));
        points.add(new Point2D(408, 444));
        points.add(new Point2D(456, 402));
        points.add(new Point2D(464, 382));
        points.add(new Point2D(476, 370));
        points.add(new Point2D(489, 312));
        points.add(new Point2D(500, 303));
        points.add(new Point2D(621, 303));
        points.add(new Point2D(621, 456));
        points.add(new Point2D(21, 456));
    }

    /**
     * Renders map.
     * @param gc graphics context of window
     */
    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(Color.rgb(0, 170, 0));
        gc.fillPolygon(points.stream().map(Point2D::getX).mapToDouble(i->i).toArray(), points.stream().map(Point2D::getY).mapToDouble(i->i).toArray(), points.size());
    }

    /**
     * Finds intersection of ray starting in <i>position</i> going in
     * direction <i>dir</i> with surface.
     * @param position start point of ray
     * @param dir ray direction
     * @return pair consisting of point of intersection and normal vector to surface at that point
     */
    public List<Point2D> getIntersection(Point2D position, Point2D dir) {
        Point2D res = Point2D.ZERO;
        Point2D norm = Point2D.ZERO;
        for (int i = 0; i < points.size(); i++) {
            Point2D A = points.get(i);
            Point2D B = points.get((i + 1) % points.size());
            double D = dir.getX() * (A.getY() - B.getY()) - dir.getY() * (A.getX() - B.getX());
            double Dl = (A.getX() - position.getX()) * (A.getY() - B.getY()) - (A.getY() - position.getY()) * (A.getX() - B.getX());
            double l = Dl / D;
            Point2D intersectionPoint = position.add(dir.multiply(l));
            if (min(A.getX(), B.getX()) <= intersectionPoint.getX() &&
                min(A.getY(), B.getY()) <= intersectionPoint.getY() &&
                max(A.getX(), B.getX()) >= intersectionPoint.getX() &&
                max(A.getY(), B.getY()) >= intersectionPoint.getY()) {
                if (res == Point2D.ZERO || position.distance(intersectionPoint) < position.distance(res)) {
                    res = intersectionPoint;
                    norm = new Point2D(A.getY() - B.getY(), B.getX() - A.getX());
                }
            }
        }
        return Arrays.asList(res, norm);
    }
}
