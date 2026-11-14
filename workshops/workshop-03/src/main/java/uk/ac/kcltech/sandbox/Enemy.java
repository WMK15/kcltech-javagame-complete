package uk.ac.kcltech.sandbox;

import static uk.ac.kcltech.sandbox.GameConfig.*;

public final class Enemy {
    public static final double WIDTH = 24;
    public static final double HEIGHT = 30;

    private double x;
    private double y;
    private double vy;
    private int direction = -1;

    public Enemy(double x) {
        this.x = x;
        this.y = 16 * TILE - HEIGHT;
    }

    public double x() { return x; }
    public double y() { return y; }

    public void update(World world, double dt) {
        double dx = direction * 65 * dt;
        double aheadX = direction > 0 ? x + WIDTH + 2 : x - 2;
        // Turn at walls and ledges rather than walking off the platform.
        if (world.collides(x + dx, y, WIDTH, HEIGHT)
                || !world.collides(aheadX, y + HEIGHT + 4, 2, 2)) {
            direction *= -1;
        } else {
            x += dx;
        }
        vy += GRAVITY * dt;
        double dy = vy * dt;
        int steps = Math.max(1, (int) Math.ceil(Math.abs(dy) / 4));
        for (int i = 0; i < steps; i++) {
            if (world.collides(x, y + dy / steps, WIDTH, HEIGHT)) {
                vy = 0;
                break;
            }
            y += dy / steps;
        }
    }
}
