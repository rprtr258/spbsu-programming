package com.rprtr258;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.*;

public class GraphicsAdapter {
    private GraphicsContext gc;
    private final Font font = Font.font("Helvetica", FontWeight.BOLD, 20);

    public GraphicsAdapter(GraphicsContext gc) {
        this.gc = gc;
    }

    public void clear() {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
    }

    public void strokeLine(double xa, double ya, double xb, double yb) {
        gc.strokeLine(xa, ya, xb, yb);
    }

    public void strokeRect(double xa, double ya, double xb, double yb) {
        gc.strokeRect(xa, ya, xb, yb);
    }

    public void fillArc(double x, double y, double w, double h, double startAngle, double arcExtent, ArcType closure) {
        gc.fillArc(x, y, w, h, startAngle, arcExtent, closure);
    }

    public void fillPolygon(double[] xs, double[] ys, int n) {
        gc.fillPolygon(xs, ys, n);
    }

    public void fillRect(double xa, double ya, double xb, double yb, Color color) {
        setFill(color);
        fillRect(xa, ya, xb, yb);
    }

    public void fillRect(double xa, double ya, double xb, double yb) {
        gc.fillRect(xa, ya, xb, yb);
    }

    public void fillCircle(double x, double y, double r) {
        gc.fillOval(x - r, y - r, 2 * r, 2 * r);
    }

    public void fillText(String text, double x, double y, Color color) {
        gc.setFill(color);
        gc.setFont(font);
        gc.fillText(text, x, y);
    }

    public void setStroke(Color color) {
        gc.setStroke(color);
    }

    public void setLineWidth(int width) {
        gc.setLineWidth(width);
    }

    public void setFill(Color color) {
        gc.setFill(color);
    }
}
