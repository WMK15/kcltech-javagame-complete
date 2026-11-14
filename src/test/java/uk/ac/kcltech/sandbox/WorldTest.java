package uk.ac.kcltech.sandbox;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WorldTest {
    @Test void miningRemovesSolidTile() {
        World world = new World();
        assertTrue(world.mine(0, 16));
        assertFalse(world.isSolid(0, 16));
        assertFalse(world.mine(0, 16));
    }

    @Test void canOnlyPlaceInEmptyTiles() {
        World world = new World();
        assertFalse(world.place(0, 16));
        assertTrue(world.place(0, 5));
        assertTrue(world.isSolid(0, 5));
    }

    @Test void outsideWorldIsSolidAndCannotBeModified() {
        World world = new World();
        assertTrue(world.isSolid(-1, 3));
        assertFalse(world.mine(-1, 3));
        assertFalse(world.place(-1, 3));
    }

    @Test void rectangleCollisionDetectsTerrain() {
        World world = new World();
        assertFalse(world.collides(0, 0, 24, 24));
        assertTrue(world.collides(0, 16 * GameConfig.TILE, 24, 24));
    }
}
