package uk.ac.kcltech.sandbox;

import static uk.ac.kcltech.sandbox.GameConfig.*;

/** A player object with horizontal movement; its height stays fixed this week. */
public final class Player {
    public static final double WIDTH = 24;
    public static final double HEIGHT = 42;

    private double x = 96;
    private final double y = SURFACE_ROW * TILE - HEIGHT;
    private boolean facingRight = true;

    public double x() { return x; }
    public double y() { return y; }
    public boolean facingRight() { return facingRight; }

    public void update(double dt, boolean left, boolean right) {
        double dx = ((right ? 1 : 0) - (left ? 1 : 0)) * MOVE_SPEED * dt;
        if (dx != 0) facingRight = dx > 0;
        x = Math.max(0, Math.min(COLUMNS * TILE - WIDTH, x + dx));
    }
}
