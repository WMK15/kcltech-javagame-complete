package uk.ac.kcltech.sandbox;

import static uk.ac.kcltech.sandbox.GameConfig.*;

public final class Player {
    public static final double WIDTH = 24;
    public static final double HEIGHT = 42;

    private double x = 96;
    private double y = 16 * TILE - HEIGHT;
    private double vy;
    private boolean grounded;
    private boolean facingRight = true;

    public double x() { return x; }
    public double y() { return y; }
    public boolean facingRight() { return facingRight; }

    public void update(World world, double dt, boolean left, boolean right, boolean jump) {
        double dx = ((right ? 1 : 0) - (left ? 1 : 0)) * MOVE_SPEED * dt;
        if (dx != 0) facingRight = dx > 0;
        moveHorizontally(world, dx);

        if (jump && grounded) {
            vy = JUMP_SPEED;
            grounded = false;
        }
        vy += GRAVITY * dt;
        double dy = vy * dt;
        grounded = false;
        moveVertically(world, dy);
    }

    private void moveHorizontally(World world, double amount) {
        int steps = Math.max(1, (int) Math.ceil(Math.abs(amount) / 4));
        double step = amount / steps;
        for (int i = 0; i < steps; i++) {
            if (world.collides(x + step, y, WIDTH, HEIGHT)) break;
            x += step;
        }
    }

    private void moveVertically(World world, double amount) {
        int steps = Math.max(1, (int) Math.ceil(Math.abs(amount) / 4));
        double step = amount / steps;
        for (int i = 0; i < steps; i++) {
            if (world.collides(x, y + step, WIDTH, HEIGHT)) {
                if (step > 0) grounded = true;
                vy = 0;
                return;
            }
            y += step;
        }
    }
}
