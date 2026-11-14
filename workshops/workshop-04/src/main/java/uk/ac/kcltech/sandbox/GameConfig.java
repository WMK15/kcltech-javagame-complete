package uk.ac.kcltech.sandbox;

public final class GameConfig {
    private GameConfig() {}
    public static final int TILE = 32;
    public static final int COLUMNS = 80;
    public static final int ROWS = 20;
    public static final int ENEMY_COUNT = 6;
    public static final int WIDTH = 960;
    public static final int HEIGHT = ROWS * TILE;
    public static final double GRAVITY = 1250;
    public static final double MOVE_SPEED = 210;
    public static final double JUMP_SPEED = -475;
}
