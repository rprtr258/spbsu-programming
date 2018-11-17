package com.rprtr258;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.*;

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

    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(Color.rgb(0, 170, 0));
        gc.fillPolygon(points.stream().map(Point2D::getX).mapToDouble(i->i).toArray(), points.stream().map(Point2D::getY).mapToDouble(i->i).toArray(), points.size());
    }
}
