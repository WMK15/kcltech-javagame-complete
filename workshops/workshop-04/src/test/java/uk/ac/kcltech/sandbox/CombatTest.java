package uk.ac.kcltech.sandbox;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CombatTest {
    @Test void unarmedHitsTakeThreeAttacksAndSwordDamageDefeatsImmediately() {
        Enemy unarmedTarget = new Enemy(18 * GameConfig.TILE);
        unarmedTarget.damage(1);
        assertEquals(2, unarmedTarget.health());
        assertTrue(unarmedTarget.alive());
        unarmedTarget.damage(1);
        assertTrue(unarmedTarget.alive());
        unarmedTarget.damage(1);
        assertFalse(unarmedTarget.alive());

        Inventory inventory = new Inventory();
        for (int i = 0; i < 3; i++) inventory.collect(World.Tile.WOOD);
        assertTrue(inventory.craftSword());
        Enemy swordTarget = new Enemy(20 * GameConfig.TILE);
        swordTarget.damage(inventory.hasSword() ? 3 : 1);
        assertFalse(swordTarget.alive());
        swordTarget.damage(3);
        assertEquals(0, swordTarget.health());
    }

    @Test void attackCooldownExpiresAndMovementChangesFacingDirection() {
        World world = new World();
        Player player = new Player();
        assertTrue(player.canAttack());
        player.attacked();
        assertFalse(player.canAttack());
        player.update(world, 0.2, false, false, false);
        assertFalse(player.canAttack());
        player.update(world, 0.16, true, false, false);
        assertTrue(player.canAttack());
        assertFalse(player.facingRight());
        player.update(world, 0.02, false, true, false);
        assertTrue(player.facingRight());
    }

    @Test void defeatedEnemiesStopMoving() {
        World world = new World();
        Enemy enemy = new Enemy(18 * GameConfig.TILE);
        enemy.damage(3);
        double x = enemy.x();
        double y = enemy.y();
        enemy.update(world, 0.033);
        assertEquals(x, enemy.x());
        assertEquals(y, enemy.y());
    }
}
