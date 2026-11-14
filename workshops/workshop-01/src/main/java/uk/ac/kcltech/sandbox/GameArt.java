package uk.ac.kcltech.sandbox;

import javafx.scene.image.Image;
import java.io.InputStream;

/** Loads this workshop's textures from the supplied classpath assets. */
public final class GameArt {
    private final Image dirt = load("blocks/dirt.png");
    private final Image grass = load("blocks/grass.png");
    private final Image stone = load("blocks/stone.png");
    private final Image sky = load("environment/sky_tile.png");
    private final Image playerIdle = load("player/idle.png");
    private final Image playerStep = load("player/step.png");

    private Image load(String path) {
        String resource = "/assets/" + path;
        try (InputStream stream = GameArt.class.getResourceAsStream(resource)) {
            if (stream == null) throw new IllegalStateException("Missing game asset: " + resource);
            Image result = new Image(stream);
            if (result.isError()) throw new IllegalStateException("Invalid game asset: " + resource, result.getException());
            return result;
        } catch (java.io.IOException error) {
            throw new IllegalStateException("Unable to read game asset: " + resource, error);
        }
    }

    public Image dirt() { return dirt; }
    public Image grass() { return grass; }
    public Image stone() { return stone; }
    public Image sky() { return sky; }
    public Image playerIdle() { return playerIdle; }
    public Image playerStep() { return playerStep; }
}
