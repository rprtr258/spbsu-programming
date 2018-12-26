package com.rprtr258;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.*;

import static jdk.nashorn.internal.objects.NativeMath.min;

public class GraphicsAdapter {
    private GraphicsContext gc;
    private final Font font = Font.font("Helvetica", FontWeight.BOLD, 20);

    public GraphicsAdapter(GraphicsContext gc) {
        this.gc = gc;
    }

    public void clear() {
        gc.clearRect(0, 0, gc.getCanvas().getScene().getWidth(), gc.getCanvas().getScene().getHeight());
    }

    public void strokeLine(double xa, double ya, double xb, double yb) {
        xa = fixX(xa);
        ya = fixY(ya);
        xb = fixX(xb);
        yb = fixY(yb);
        gc.strokeLine(xa, ya, xb, yb);
    }

    public void strokeRect(double xa, double ya, double xb, double yb) {
        xa = fixX(xa);
        ya = fixY(ya);
        xb = fixX(xb);
        yb = fixY(yb);
        gc.strokeRect(xa, ya, xb, yb);
    }

    public void fillArc(double x, double y, double r, double startAngle, double arcExtent, ArcType closure) {
        x = fixX(x);
        y = fixY(y);
        r = fixR(r);
        gc.fillArc(x - r / 2, y - r / 2, r, r, startAngle, arcExtent, closure);
    }

    public void fillPolygon(double[] xs, double[] ys, int n) {
        for (int i = 0; i < n; i++) {
            xs[i] = fixX(xs[i]);
            ys[i] = fixY(ys[i]);
        }
        gc.fillPolygon(xs, ys, n);
    }

    public void fillRect(double xa, double ya, double xb, double yb, Color color) {
        xa = fixX(xa);
        ya = fixY(ya);
        xb = fixX(xb);
        yb = fixY(yb);
        setFill(color);
        fillRect(xa, ya, xb, yb);
    }

    public void fillRect(double xa, double ya, double xb, double yb) {
        xa = fixX(xa);
        ya = fixY(ya);
        xb = fixX(xb);
        yb = fixY(yb);
        gc.fillRect(xa, ya, xb, yb);
    }

    public void fillCircle(double x, double y, double r) {
        x = fixX(x);
        y = fixY(y);
        gc.fillOval(x - r, y - r, 2 * r, 2 * r);
    }

    public void fillText(String text, double x, double y, Color color) {
        x = fixX(x);
        y = fixY(y);
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

    public double getWidth() {
        return gc.getCanvas().getScene().getWidth();
    }

    public double getHeight() {
        return gc.getCanvas().getScene().getHeight();
    }

    private double fixR(double r) {
        return r * min(getWidth() / 640, getHeight() / 480);
    }

    public double unfixX(double x) {
        return x * 640.0 / getWidth();
    }

    public double unfixY(double y) {
        return y * 480.0 / getHeight();
    }

    public double fixX(double x) {
        return x / 640.0 * getWidth();
    }

    public double fixY(double y) {
        return y / 480.0 * getHeight();
    }
}
