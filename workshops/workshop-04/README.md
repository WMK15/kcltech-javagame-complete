# Workshop 4: Combat, finalisation and presentations

This is the runnable **end-of-Workshop-4 reference project** for KCL Tech Academy: Java Game Development. It includes the mechanics from Workshops 1-3 plus player attacks, enemy health, damage, defeat and a completion message. There is one enemy type (six instances), one recipe and three resource counters.

## Open and run in IntelliJ IDEA

1. Open **this folder** as a Maven project, using its `pom.xml`.
2. Set the Project SDK and Maven runner JRE to **JDK 21**.
3. Reload Maven dependencies. JavaFX is supplied by Maven; no manual SDK download is needed.
4. In the Maven tool window, run **Plugins → javafx → javafx:run**.

With Maven installed, run these commands from this folder:

```sh
mvn clean javafx:run
mvn test
```

The game needs a graphical desktop. Tests do not launch JavaFX. This folder contains all required game assets and can be used independently for the Academy course use described below. See the separate [asset provenance and CC0 terms](ASSET_LICENSE.md).

## Controls

| Input | Action |
| --- | --- |
| A / D or left / right arrows | Move |
| Space / W / up arrow | Jump |
| Left click | Mine within four tiles; grass drops dirt |
| 1 / 2 / 3 (or numpad) | Select dirt / stone / wood |
| Right click | Place the selected material, spending one collected block |
| E | Craft one sword from three wood |
| F | Attack in front of the player, with a short cooldown |

Mine three wood blocks, craft the sword and defeat every slime enemy. An unarmed attack deals one damage; the sword deals three. Each enemy starts with three health. The HUD shows materials, selected block, sword ownership and enemies remaining. Placing a block on the player or a living enemy is rejected.

## Two-hour session

| Minutes | Activity |
| --- | --- |
| 0-10 | Run the Workshop 3 game and recap the integration points |
| 10-30 | Reuse facing direction; add attack input, reach and cooldown |
| 30-50 | Add enemy health, damage, defeat and the remaining-enemy counter |
| 50-75 | Integrate and test movement, mining, building, crafting and combat |
| 75-90 | Fix issues and prepare a short demonstration |
| **90-120** | **Student presentations; reserve this entire final 30 minutes** |

Use the completed project as an instructor reference or recovery checkpoint. Students continue their own Workshop 3 work and personalise it; they do not need to replace it with this source.

## Checkpoint and presentation

Demonstrate moving, jumping, mining a material, placing it, collecting three wood, crafting a sword and defeating an enemy. Check that attacks fail outside their range and that the game announces when all enemies are defeated. Explain one implementation decision and one bug you fixed.

`SandboxGame` integrates inputs, the game loop, rendering and attacks. `Player` reuses facing direction and adds attack cooldown; `Enemy` adds health and damage. `World`, `Inventory`, `Collision`, `GameArt` and `GameConfig` retain the earlier systems.

## After the course

The required deliverable is complete. Procedural terrain, extra recipes, more complex AI, an inventory screen, multiplayer and save/load are optional independent extensions, outside the eight-hour core series.

## Course use

This is KCL Tech Academy teaching material for course participants and facilitators. It is not an open-source project and does not accept external contributions. The [course licensing notice](https://github.com/WMK15/kcltech-javagame-complete/blob/main/LICENSING.md) applies to the code and teaching materials; a copy is included in workshop ZIPs. The separate CC0 artwork terms remain in [ASSET_LICENSE.md](ASSET_LICENSE.md).
