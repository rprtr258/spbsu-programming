package com.rprtr258.client;

import javafx.scene.canvas.GraphicsContext;

/**
 * Interface for renderable entities.
 */
public interface Renderable {
    /**
     * Renders object
     * @param gc graphics context of window
     */
    void render(GraphicsContext gc);
}
