package uk.ac.kcltech.sandbox;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {
    @Test void craftingConsumesExactlyThreeDirt() {
        Inventory inventory = new Inventory();
        assertFalse(inventory.craftSword());
        for (int i = 0; i < 4; i++) inventory.collectDirt();
        assertTrue(inventory.craftSword());
        assertEquals(1, inventory.dirt());
        assertTrue(inventory.hasSword());
        assertFalse(inventory.craftSword());
    }

    @Test void cannotPlaceWithoutResource() {
        Inventory inventory = new Inventory();
        assertFalse(inventory.spendDirt());
        inventory.collectDirt();
        assertTrue(inventory.spendDirt());
        assertEquals(0, inventory.dirt());
    }
}
