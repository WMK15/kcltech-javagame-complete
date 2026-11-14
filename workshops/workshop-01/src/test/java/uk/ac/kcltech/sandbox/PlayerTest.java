package uk.ac.kcltech.sandbox;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    @Test void movementUsesSecondsAndLeavesHeightFixed() {
        Player player = new Player();
        double startX = player.x();
        double startY = player.y();
        player.update(0.5, false, true);
        assertEquals(startX + GameConfig.MOVE_SPEED * 0.5, player.x(), 0.001);
        assertEquals(startY, player.y());
        assertTrue(player.facingRight());
        player.update(0.25, true, false);
        assertEquals(startX + GameConfig.MOVE_SPEED * 0.25, player.x(), 0.001);
        assertFalse(player.facingRight());
    }

    @Test void oppositeKeysCancelAndReleasingKeysStopsMovement() {
        Player player = new Player();
        double startX = player.x();
        player.update(1, true, true);
        player.update(1, false, false);
        assertEquals(startX, player.x());
    }

    @Test void playerCannotLeaveEitherEndOfTheMap() {
        Player player = new Player();
        player.update(100, true, false);
        assertEquals(0, player.x());
        player.update(100, false, true);
        assertEquals(GameConfig.COLUMNS * GameConfig.TILE - Player.WIDTH, player.x());
    }
}
