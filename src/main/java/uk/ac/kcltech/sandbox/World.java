package uk.ac.kcltech.sandbox;

import static uk.ac.kcltech.sandbox.GameConfig.*;

public final class World {
    public enum Tile { AIR, DIRT, GRASS }

    private final Tile[][] tiles = new Tile[ROWS][COLUMNS];

    public World() {
        for (int row = 0; row < ROWS; row++) {
            for (int column = 0; column < COLUMNS; column++) {
                int surface = 16 + (column % 17 == 10 ? 1 : 0);
                tiles[row][column] = row < surface ? Tile.AIR :
                        row == surface ? Tile.GRASS : Tile.DIRT;
            }
        }
    }

    public Tile get(int column, int row) {
        if (row < 0 || column < 0 || column >= COLUMNS || row >= ROWS) {
            return Tile.DIRT; // Out of bounds is a solid boundary.
        }
        return tiles[row][column];
    }

    public boolean isSolid(int column, int row) {
        return get(column, row) != Tile.AIR;
    }

    public boolean mine(int column, int row) {
        if (!inside(column, row) || tiles[row][column] == Tile.AIR) return false;
        tiles[row][column] = Tile.AIR;
        return true;
    }

    public boolean place(int column, int row) {
        if (!inside(column, row) || tiles[row][column] != Tile.AIR) return false;
        tiles[row][column] = Tile.DIRT;
        return true;
    }

    private boolean inside(int column, int row) {
        return column >= 0 && column < COLUMNS && row >= 0 && row < ROWS;
    }

    public boolean collides(double x, double y, double width, double height) {
        int minColumn = (int) Math.floor(x / TILE);
        int maxColumn = (int) Math.floor((x + width - 0.001) / TILE);
        int minRow = (int) Math.floor(y / TILE);
        int maxRow = (int) Math.floor((y + height - 0.001) / TILE);
        for (int row = minRow; row <= maxRow; row++) {
            for (int column = minColumn; column <= maxColumn; column++) {
                if (isSolid(column, row)) return true;
            }
        }
        return false;
    }
}
