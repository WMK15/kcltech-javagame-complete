package uk.ac.kcltech.sandbox;

import static uk.ac.kcltech.sandbox.GameConfig.*;

/** A fixed tile grid: the first array index is the row, the second the column. */
public final class World {
    public enum Tile { AIR, DIRT, GRASS, STONE }

    private final Tile[][] tiles = new Tile[ROWS][COLUMNS];

    public World() {
        for (int row = 0; row < ROWS; row++) {
            for (int column = 0; column < COLUMNS; column++) {
                tiles[row][column] = row < SURFACE_ROW ? Tile.AIR :
                        row == SURFACE_ROW ? Tile.GRASS :
                        row == SURFACE_ROW + 1 ? Tile.DIRT : Tile.STONE;
            }
        }
    }

    public Tile get(int column, int row) {
        if (column < 0 || column >= COLUMNS || row < 0 || row >= ROWS) {
            return Tile.AIR;
        }
        return tiles[row][column];
    }
}
