package uk.ac.kcltech.sandbox;

/** Dimensions shared by the tile world and the game window. */
public final class GameConfig {
    private GameConfig() {}

    public static final int TILE = 32;
    public static final int COLUMNS = 80;
    public static final int ROWS = 20;
    public static final int SURFACE_ROW = 16;
    public static final int WIDTH = 960;
    public static final int HEIGHT = ROWS * TILE;
    public static final double MOVE_SPEED = 210;
}
