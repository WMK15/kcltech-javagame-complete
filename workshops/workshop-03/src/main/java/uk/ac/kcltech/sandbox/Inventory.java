package uk.ac.kcltech.sandbox;

/** Three block resources, one recipe: three wood blocks make a sword. */
public final class Inventory {
    private int dirt;
    private int stone;
    private int wood;
    private boolean sword;

    public boolean hasSword() { return sword; }

    public int count(World.Tile material) {
        if (material == null) return 0;
        return switch (material) {
            case DIRT, GRASS -> dirt;
            case STONE -> stone;
            case WOOD -> wood;
            case AIR -> 0;
        };
    }

    public void collect(World.Tile material) {
        if (material == null) return;
        switch (material) {
            case DIRT, GRASS -> dirt++;
            case STONE -> stone++;
            case WOOD -> wood++;
            case AIR -> { }
        }
    }

    public boolean spend(World.Tile material) {
        if (material == null || count(material) == 0) return false;
        switch (material) {
            case DIRT -> dirt--;
            case STONE -> stone--;
            case WOOD -> wood--;
            default -> { return false; }
        }
        return true;
    }

    public boolean craftSword() {
        if (sword || wood < 3) return false;
        wood -= 3;
        sword = true;
        return true;
    }
}
