package com.rprtr258;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.*;

import static jdk.nashorn.internal.objects.NativeMath.min;

/**
 * Adapter for GraphicsContext class
 */
public class GraphicsAdapter {
    private GraphicsContext gc;
    private final Font font = Font.font("Helvetica", FontWeight.BOLD, 20);

    /**
     * GraphicsAdapter class constructor
     */
    public GraphicsAdapter(GraphicsContext gc) {
        this.gc = gc;
    }

    /**
     * Clears screen
     */
    public void clear() {
        gc.clearRect(0, 0, gc.getCanvas().getScene().getWidth(), gc.getCanvas().getScene().getHeight());
    }

    /**
     * Draws line
     * @param xa x coordinate of begin point
     * @param ya y coordinate of begin point
     * @param xb x coordinate of end point
     * @param yb y coordinate of end point
     */
    public void strokeLine(double xa, double ya, double xb, double yb) {
        xa = fixX(xa);
        ya = fixY(ya);
        xb = fixX(xb);
        yb = fixY(yb);
        gc.strokeLine(xa, ya, xb, yb);
    }

    /**
     * Draws rectangle
     * @param xa x coordinate of one point
     * @param ya y coordinate of one point
     * @param xb x coordinate of another point
     * @param yb y coordinate of another point
     */
    public void strokeRect(double xa, double ya, double xb, double yb) {
        xa = fixX(xa);
        ya = fixY(ya);
        xb = fixX(xb);
        yb = fixY(yb);
        gc.strokeRect(xa, ya, xb, yb);
    }

    /**
     * Draws circle part
     * @param x x coordinate of center
     * @param y y coordinate of center
     * @param r radius
     * @param startAngle beginning angle of arc
     * @param arcExtent size of arc
     * @param closure arc type
     */
    public void fillArc(double x, double y, double r, double startAngle, double arcExtent, ArcType closure) {
        x = fixX(x);
        y = fixY(y);
        r = fixR(r);
        gc.fillArc(x - r / 2, y - r / 2, r, r, startAngle, arcExtent, closure);
    }

    /**
     * Draws polygon
     * @param xs x's coordinates of points
     * @param ys y's coordinates of points
     * @param n number of points
     */
    public void fillPolygon(double[] xs, double[] ys, int n) {
        for (int i = 0; i < n; i++) {
            xs[i] = fixX(xs[i]);
            ys[i] = fixY(ys[i]);
        }
        gc.fillPolygon(xs, ys, n);
    }

    /**
     * Draws rectangle with given color
     * @param xa x coordinate of one point
     * @param ya y coordinate of one point
     * @param xb x coordinate of another point
     * @param yb y coordinate of another point
     * @param color color to fill
     */
    public void fillRect(double xa, double ya, double xb, double yb, Color color) {
        xa = fixX(xa);
        ya = fixY(ya);
        xb = fixX(xb);
        yb = fixY(yb);
        setFill(color);
        fillRect(xa, ya, xb, yb);
    }

    /**
     * Draws rectangle filled with color
     * @param xa x coordinate of one point
     * @param ya y coordinate of one point
     * @param xb x coordinate of another point
     * @param yb y coordinate of another point
     */
    public void fillRect(double xa, double ya, double xb, double yb) {
        xa = fixX(xa);
        ya = fixY(ya);
        xb = fixX(xb);
        yb = fixY(yb);
        gc.fillRect(xa, ya, xb, yb);
    }

    /**
     * Draws circle
     * @param x x coordinate of center
     * @param y y coordinate of center
     * @param r radius
     */
    public void fillCircle(double x, double y, double r) {
        x = fixX(x);
        y = fixY(y);
        gc.fillOval(x - r, y - r, 2 * r, 2 * r);
    }

    /**
     * Draws text
     * @param text text to draw
     * @param x x coordinate of text
     * @param y y coordinate of text
     * @param color color of text
     */
    public void fillText(String text, double x, double y, Color color) {
        x = fixX(x);
        y = fixY(y);
        gc.setFill(color);
        gc.setFont(font);
        gc.fillText(text, x, y);
    }

    /**
     * Sets color of lines to use if not specified
     * @param color color to use
     */
    public void setStroke(Color color) {
        gc.setStroke(color);
    }

    /**
     * Sets width of lines
     * @param width width to use
     */
    public void setLineWidth(int width) {
        gc.setLineWidth(width);
    }

    /**
     * Sets color to fill if not specified
     * @param color color to use
     */
    public void setFill(Color color) {
        gc.setFill(color);
    }

    /**
     * @return width of screen
     */
    public double getWidth() {
        return gc.getCanvas().getScene().getWidth();
    }

    /**
     * @return height of screen
     */
    public double getHeight() {
        return gc.getCanvas().getScene().getHeight();
    }

    /**
     * Adjusts x coordinate to screen size
     * @param x x coordinate
     * @return adjusted x
     */
    public double fixX(double x) {
        return x / 640.0 * getWidth();
    }

    /**
     * Adjusts y coordinate to screen size
     * @param y y coordinate
     * @return adjusted y
     */
    public double fixY(double y) {
        return y / 480.0 * getHeight();
    }

    /**
     * Adjusts screen x coordinate to game coordinates
     * @param x x coordinate
     * @return adjusted x
     */
    public double unfixX(double x) {
        return x * 640.0 / getWidth();
    }

    /**
     * Adjusts screen y coordinate to game coordinates
     * @param y y coordinate
     * @return adjusted y
     */
    public double unfixY(double y) {
        return y * 480.0 / getHeight();
    }

    private double fixR(double r) {
        return r * min(getWidth() / 640, getHeight() / 480);
    }
}
