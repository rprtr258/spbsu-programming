package com.rprtr258;

import javafx.scene.canvas.Canvas;

/**
 * Resizable canvas that can change size
 */
public class ResizableCanvas extends Canvas {
    /**
     * Constructor
     */
    public ResizableCanvas(int width, int height) {
        super(width, height);
    }

    /**
     * Min height
     * @param width unused
     * @return min height of canvas
     */
    @Override
    public double minHeight(double width) {
        return 480;
    }

    /**
     * Min height
     * @param width unused
     * @return max height of canvas
     */
    @Override
    public double maxHeight(double width) {
        return 1000;
    }

    /**
     * Min width
     * @param height unused
     * @return min width of canvas
     */
    @Override
    public double minWidth(double height) {
        return 640;
    }

    /**
     * Max width
     * @param height unused
     * @return max width of canvas
     */
    @Override
    public double maxWidth(double height) {
        return 10000;
    }

    /**
     * @return true due to resizability
     */
    @Override
    public boolean isResizable() {
        return true;
    }

    /**
     * Resizes canvas
     * @param width new width
     * @param height new height
     */
    @Override
    public void resize(double width, double height) {
        super.setWidth(width);
        super.setHeight(height);
    }
}
