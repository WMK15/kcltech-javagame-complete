package uk.ac.kcltech.sandbox;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private static final double DT = 1.0 / 60;

    @Test void groundedPlayerJumpsAndReturnsToTerrain() {
        World world = new World();
        Player player = new Player();
        double groundY = player.y();
        player.update(world, DT, false, false, false);
        player.update(world, DT, false, false, true);
        assertTrue(player.y() < groundY);
        for (int frame = 0; frame < 120; frame++) {
            player.update(world, DT, false, false, false);
            assertFalse(world.collides(player.x(), player.y(), Player.WIDTH, Player.HEIGHT));
        }
        assertEquals(groundY, player.y(), 4);
    }

    @Test void jumpCannotRestartInMidAir() {
        World world = new World();
        Player released = new Player();
        Player pressedAgain = new Player();
        for (Player player : new Player[] {released, pressedAgain}) {
            player.update(world, DT, false, false, false);
            player.update(world, DT, false, false, true);
        }
        for (int frame = 0; frame < 10; frame++) {
            released.update(world, DT, false, false, false);
            pressedAgain.update(world, DT, false, false, true);
        }
        assertEquals(released.y(), pressedAgain.y(), 0.0001);
    }

    @Test void wallStopsMovementAndMiningItOpensAPath() {
        World world = new World();
        Player player = new Player();
        world.place(4, 14, World.Tile.DIRT);
        world.place(4, 15, World.Tile.DIRT);
        for (int frame = 0; frame < 30; frame++) player.update(world, DT, false, true, false);
        assertTrue(player.x() + Player.WIDTH <= 4 * GameConfig.TILE);
        assertFalse(world.collides(player.x(), player.y(), Player.WIDTH, Player.HEIGHT));
        world.mine(4, 14);
        world.mine(4, 15);
        for (int frame = 0; frame < 10; frame++) player.update(world, DT, false, true, false);
        assertTrue(player.x() > 4 * GameConfig.TILE);
    }

    @Test void miningSupportMakesPlayerFallOntoTheNextRow() {
        World world = new World();
        Player player = new Player();
        double originalY = player.y();
        world.mine(3, 16);
        for (int frame = 0; frame < 60; frame++) player.update(world, DT, false, false, false);
        assertEquals(originalY + GameConfig.TILE, player.y(), 4);
        assertFalse(world.collides(player.x(), player.y(), Player.WIDTH, Player.HEIGHT));
    }

    @Test void worldBoundaryStopsThePlayer() {
        World world = new World();
        Player player = new Player();
        for (int frame = 0; frame < 120; frame++) player.update(world, DT, true, false, false);
        assertTrue(player.x() >= 0);
        assertTrue(player.x() < 4);
    }
}
