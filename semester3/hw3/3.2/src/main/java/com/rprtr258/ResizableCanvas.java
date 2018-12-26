package com.rprtr258;

import javafx.scene.canvas.Canvas;

public class ResizableCanvas extends Canvas {
    public ResizableCanvas(int width, int height) {
        super(width, height);
    }

    @Override
    public double minHeight(double width) {
        return 480;
    }

    @Override
    public double maxHeight(double width) {
        return 1000;
    }

    @Override
    public double minWidth(double height) {
        return 640;
    }

    @Override
    public double maxWidth(double height) {
        return 10000;
    }

    @Override
    public boolean isResizable() {
        return true;
    }

    @Override
    public void resize(double width, double height) {
        super.setWidth(width);
        super.setHeight(height);
    }
}
