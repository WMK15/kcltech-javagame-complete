package uk.ac.kcltech.sandbox;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CollisionTest {
    @Test void overlappingRectanglesCollide() {
        assertTrue(Collision.overlaps(0, 0, 10, 10, 5, 5, 10, 10));
    }
    @Test void touchingEdgesDoNotCountAsOverlap() {
        assertFalse(Collision.overlaps(0, 0, 10, 10, 10, 0, 10, 10));
    }
}
