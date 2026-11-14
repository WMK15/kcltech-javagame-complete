package uk.ac.kcltech.sandbox;

import javafx.scene.image.Image;
import java.io.InputStream;

/** Loads bundled pixel-art from classpath resources (works in IntelliJ and packaged builds). */
public final class GameArt {
    private final Image dirt = load("blocks/dirt.png");
    private final Image grass = load("blocks/grass.png");
    private final Image stone = load("blocks/stone.png");
    private final Image sand = load("blocks/sand.png");
    private final Image wood = load("blocks/wood.png");
    private final Image leaves = load("blocks/leaves.png");
    private final Image gold = load("blocks/gold.png");
    private final Image sky = load("environment/sky_tile.png");
    private final Image playerIdle = load("player/idle.png");
    private final Image playerStep = load("player/step.png");
    private final Image enemy = load("enemy/slime.png");
    private final Image sword = load("items/sword.png");

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
    public Image sand() { return sand; }
    public Image wood() { return wood; }
    public Image leaves() { return leaves; }
    public Image gold() { return gold; }
    public Image sky() { return sky; }
    public Image playerIdle() { return playerIdle; }
    public Image playerStep() { return playerStep; }
    public Image enemy() { return enemy; }
    public Image sword() { return sword; }
}
