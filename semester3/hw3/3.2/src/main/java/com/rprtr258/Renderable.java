package com.rprtr258;

/**
 * Interface for renderable entities.
 */
public interface Renderable {
    /**
     * Renders object
     * @param gc graphics context of window
     */
    void render(GraphicsAdapter gc);
}
