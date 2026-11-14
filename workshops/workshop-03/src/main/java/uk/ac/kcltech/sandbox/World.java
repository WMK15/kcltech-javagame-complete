package uk.ac.kcltech.sandbox;

import static uk.ac.kcltech.sandbox.GameConfig.*;

public final class World {
    public enum Tile { AIR, DIRT, GRASS, STONE, WOOD }

    private final Tile[][] tiles = new Tile[ROWS][COLUMNS];

    public World() {
        for (int row = 0; row < ROWS; row++) {
            for (int column = 0; column < COLUMNS; column++) {
                int surface = surfaceRow(column);
                tiles[row][column] = row < surface ? Tile.AIR :
                        row == surface ? Tile.GRASS :
                        row < surface + 2 ? Tile.DIRT : Tile.STONE;
            }
        }
        for (int firstColumn = 7; firstColumn < COLUMNS; firstColumn += 16) {
            for (int column = firstColumn; column < Math.min(firstColumn + 3, COLUMNS); column++) {
                tiles[surfaceRow(column) - 1][column] = Tile.WOOD;
            }
        }
    }

    private static int surfaceRow(int column) {
        return 16 + (column % 17 == 10 ? 1 : 0);
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

    public Tile mine(int column, int row) {
        if (!inside(column, row)) return Tile.AIR;
        Tile mined = tiles[row][column];
        tiles[row][column] = Tile.AIR;
        return mined;
    }

    public boolean place(int column, int row, Tile tile) {
        if (tile == null || tile == Tile.AIR || !inside(column, row)
                || tiles[row][column] != Tile.AIR) return false;
        tiles[row][column] = tile;
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
