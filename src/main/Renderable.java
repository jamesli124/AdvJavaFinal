package main;

import javafx.scene.canvas.GraphicsContext;

/**
 * Functional interface specifying renderables
 */
@FunctionalInterface
public interface Renderable {
    /**
     * Draws Renderable onto the Canvas with GraphicsContext pen
     *
     * @param pen   GraphicsContext to draw with
     * @param cameraX X coordinate of camera for relative position
     */
    public void render(GraphicsContext pen, double cameraX);
}
