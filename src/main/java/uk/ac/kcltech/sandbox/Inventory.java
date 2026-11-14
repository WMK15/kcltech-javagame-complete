package uk.ac.kcltech.sandbox;

/** One resource, one recipe: three dirt blocks make a sword. */
public final class Inventory {
    private int dirt;
    private boolean sword;

    public int dirt() { return dirt; }
    public boolean hasSword() { return sword; }

    public void collectDirt() { dirt++; }

    public boolean spendDirt() {
        if (dirt == 0) return false;
        dirt--;
        return true;
    }

    public boolean craftSword() {
        if (sword || dirt < 3) return false;
        dirt -= 3;
        sword = true;
        return true;
    }
}
