package uk.ac.kcltech.sandbox;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WorldTest {
    @Test void terrainHasSkyGrassDirtAndStoneAcrossTheWholeMap() {
        World world = new World();
        for (int column = 0; column < GameConfig.COLUMNS; column++) {
            assertEquals(World.Tile.AIR, world.get(column, GameConfig.SURFACE_ROW - 1));
            assertEquals(World.Tile.GRASS, world.get(column, GameConfig.SURFACE_ROW));
            assertEquals(World.Tile.DIRT, world.get(column, GameConfig.SURFACE_ROW + 1));
            assertEquals(World.Tile.STONE, world.get(column, GameConfig.SURFACE_ROW + 2));
        }
    }

    @Test void playerStartsAboveTheGrass() {
        Player player = new Player();
        assertEquals(GameConfig.SURFACE_ROW * GameConfig.TILE, player.y() + Player.HEIGHT);
    }

    @Test void readingOutsideTheGridIsSafe() {
        World world = new World();
        assertEquals(World.Tile.AIR, world.get(-1, 0));
        assertEquals(World.Tile.AIR, world.get(GameConfig.COLUMNS, 0));
        assertEquals(World.Tile.AIR, world.get(0, -1));
        assertEquals(World.Tile.AIR, world.get(0, GameConfig.ROWS));
    }
}
