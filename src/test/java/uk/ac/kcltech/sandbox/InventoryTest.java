package uk.ac.kcltech.sandbox;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static uk.ac.kcltech.sandbox.World.Tile.*;

class InventoryTest {
    @Test void craftingConsumesExactlyThreeWoodAndPreservesOtherResources() {
        Inventory inventory = new Inventory();
        inventory.collect(DIRT);
        inventory.collect(STONE);
        for (int i = 0; i < 4; i++) inventory.collect(WOOD);

        assertTrue(inventory.craftSword());
        assertTrue(inventory.hasSword());
        assertEquals(1, inventory.count(WOOD));
        assertEquals(1, inventory.count(DIRT));
        assertEquals(1, inventory.count(STONE));

        for (int i = 0; i < 3; i++) inventory.collect(WOOD);
        assertFalse(inventory.craftSword());
        assertEquals(4, inventory.count(WOOD));
    }

    @Test void otherMaterialsCannotSubstituteForMissingWood() {
        Inventory inventory = new Inventory();
        for (int i = 0; i < 3; i++) {
            inventory.collect(DIRT);
            inventory.collect(STONE);
        }
        inventory.collect(WOOD);
        inventory.collect(WOOD);

        assertFalse(inventory.craftSword());
        assertFalse(inventory.hasSword());
        assertEquals(2, inventory.count(WOOD));
        assertEquals(3, inventory.count(DIRT));
        assertEquals(3, inventory.count(STONE));
    }

    @Test void placingSpendsOnlyTheChosenResourceAndCannotOverspend() {
        Inventory inventory = new Inventory();
        inventory.collect(DIRT);
        inventory.collect(STONE);
        inventory.collect(WOOD);

        assertTrue(inventory.spend(STONE));
        assertFalse(inventory.spend(STONE));
        assertEquals(0, inventory.count(STONE));
        assertEquals(1, inventory.count(DIRT));
        assertEquals(1, inventory.count(WOOD));

        assertTrue(inventory.spend(DIRT));
        assertFalse(inventory.spend(DIRT));
        assertTrue(inventory.spend(WOOD));
        assertFalse(inventory.spend(WOOD));
        assertEquals(0, inventory.count(DIRT));
        assertEquals(0, inventory.count(WOOD));
    }

    @Test void grassIsCollectedAsDirt() {
        Inventory inventory = new Inventory();
        inventory.collect(GRASS);
        inventory.collect(DIRT);

        assertEquals(2, inventory.count(DIRT));
        assertEquals(2, inventory.count(GRASS));
        assertFalse(inventory.spend(GRASS));
        assertTrue(inventory.spend(DIRT));
        assertEquals(1, inventory.count(DIRT));
        assertEquals(0, inventory.count(STONE));
        assertEquals(0, inventory.count(WOOD));
    }

    @Test void airAndMissingBlocksDoNotCreateOrConsumeResources() {
        Inventory inventory = new Inventory();
        inventory.collect(DIRT);
        inventory.collect(AIR);
        inventory.collect(null);

        assertEquals(0, inventory.count(AIR));
        assertEquals(0, inventory.count(null));
        assertFalse(inventory.spend(AIR));
        assertFalse(inventory.spend(null));
        assertEquals(1, inventory.count(DIRT));
        assertEquals(0, inventory.count(STONE));
        assertEquals(0, inventory.count(WOOD));
    }
}
