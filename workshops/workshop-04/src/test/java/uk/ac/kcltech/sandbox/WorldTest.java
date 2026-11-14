package uk.ac.kcltech.sandbox;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WorldTest {
    @Test void miningReturnsAndRemovesTheOriginalMaterial() {
        World world = new World();
        assertEquals(World.Tile.GRASS, world.mine(0, 16));
        assertFalse(world.isSolid(0, 16));
        assertEquals(World.Tile.AIR, world.mine(0, 16));
        assertEquals(World.Tile.DIRT, world.mine(0, 17));
        assertEquals(World.Tile.STONE, world.mine(0, 18));
        assertEquals(World.Tile.WOOD, world.mine(7, 15));
    }

    @Test void placementPreservesTheSelectedMaterial() {
        World world = new World();
        assertTrue(world.place(0, 5, World.Tile.DIRT));
        assertTrue(world.place(1, 5, World.Tile.STONE));
        assertTrue(world.place(2, 5, World.Tile.WOOD));
        assertEquals(World.Tile.DIRT, world.get(0, 5));
        assertEquals(World.Tile.STONE, world.get(1, 5));
        assertEquals(World.Tile.WOOD, world.get(2, 5));
        assertEquals(World.Tile.WOOD, world.mine(2, 5));
    }

    @Test void placementRejectsSolidDestinationsAndInvalidMaterials() {
        World world = new World();
        assertFalse(world.place(0, 16, World.Tile.WOOD));
        assertEquals(World.Tile.GRASS, world.get(0, 16));
        assertFalse(world.place(0, 5, World.Tile.AIR));
        assertFalse(world.place(0, 5, null));
        assertEquals(World.Tile.AIR, world.get(0, 5));
    }

    @Test void outsideWorldIsSolidAndCannotBeModified() {
        World world = new World();
        assertTrue(world.isSolid(-1, 3));
        int[][] outside = {{-1, 3}, {GameConfig.COLUMNS, 3}, {3, -1}, {3, GameConfig.ROWS}};
        for (int[] position : outside) {
            assertEquals(World.Tile.AIR, world.mine(position[0], position[1]));
            assertFalse(world.place(position[0], position[1], World.Tile.WOOD));
            assertTrue(world.isSolid(position[0], position[1]));
        }
    }

    @Test void terrainKeepsDipsWithDirtAboveStone() {
        World world = new World();
        assertEquals(World.Tile.GRASS, world.get(0, 16));
        assertEquals(World.Tile.DIRT, world.get(0, 17));
        assertEquals(World.Tile.STONE, world.get(0, 18));
        assertEquals(World.Tile.AIR, world.get(10, 16));
        assertEquals(World.Tile.GRASS, world.get(10, 17));
        assertEquals(World.Tile.DIRT, world.get(10, 18));
        assertEquals(World.Tile.STONE, world.get(10, 19));
    }

    @Test void woodPilesAreReachableAboveTheSurfaceAndPlayerSpawnIsClear() {
        World world = new World();
        for (int firstColumn = 7; firstColumn < GameConfig.COLUMNS; firstColumn += 16) {
            for (int column = firstColumn; column < firstColumn + 3; column++) {
                assertEquals(World.Tile.WOOD, world.get(column, 15));
                assertEquals(World.Tile.AIR, world.get(column, 14));
                assertEquals(World.Tile.GRASS, world.get(column, 16));
            }
        }
        Player player = new Player();
        assertFalse(world.collides(player.x(), player.y(), Player.WIDTH, Player.HEIGHT));
    }

    @Test void rectangleCollisionDetectsTerrain() {
        World world = new World();
        assertFalse(world.collides(0, 0, 24, 24));
        assertTrue(world.collides(0, 16 * GameConfig.TILE, 24, 24));
    }
}
