package uk.ac.kcltech.sandbox;

/** Pure geometry helpers; no JavaFX runtime required. */
public final class Collision {
    private Collision() {}

    public static boolean overlaps(double ax, double ay, double aw, double ah,
                                   double bx, double by, double bw, double bh) {
        return ax < bx + bw && ax + aw > bx && ay < by + bh && ay + ah > by;
    }
}
