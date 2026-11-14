package uk.ac.kcltech.sandbox;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static uk.ac.kcltech.sandbox.GameConfig.TILE;

class EnemyTest {
    @Test void patrolTurnsInsteadOfEnteringAWall() {
        World world = new World();
        Enemy enemy = new Enemy(18 * TILE);
        assertTrue(world.place(17, 15, World.Tile.DIRT));
        double startX = enemy.x();

        enemy.update(world, 0.02);
        assertEquals(startX, enemy.x());
        enemy.update(world, 0.02);

        assertTrue(enemy.x() > startX);
        assertFalse(world.collides(enemy.x(), enemy.y(), Enemy.WIDTH, Enemy.HEIGHT));
    }

    @Test void patrolTurnsBeforeWalkingOffAMinedLedge() {
        World world = new World();
        Enemy enemy = new Enemy(18 * TILE);
        world.mine(17, 16);
        double startX = enemy.x();

        enemy.update(world, 0.02);
        assertEquals(startX, enemy.x());
        enemy.update(world, 0.02);

        assertTrue(enemy.x() > startX);
        assertFalse(world.collides(enemy.x(), enemy.y(), Enemy.WIDTH, Enemy.HEIGHT));
    }
}
