# KCL Tech Academy: Java Game Development — Workshop 3

This standalone Java 21 + JavaFX project is the **completed Workshop 3 checkpoint**. It includes movement, gravity, jumping, collision, mining and building from Workshops 1–2, then adds resource collection, one crafting recipe and one enemy type with patrol movement.

## Run in IntelliJ IDEA

1. Install JDK 21 and IntelliJ IDEA.
2. Open **this folder** (`workshops/workshop-03`) as a Maven project.
3. Select JDK 21 for the Project SDK and Maven runner, then reload Maven dependencies.
4. Run **Maven → Plugins → javafx → javafx:run**.

From a terminal in this folder:

```sh
mvn clean javafx:run
```

JavaFX and all pixel-art assets are supplied by the project. A desktop display is required to play. Run the logic tests with `mvn test`.

## Controls and checkpoint

| Input | Action |
| --- | --- |
| A / D or left / right arrows | Move |
| Space / W / up arrow | Jump |
| Left click | Mine within four tiles and collect the material |
| 1 / 2 / 3, including numpad | Select dirt / stone / wood |
| Right click | Place the selected material, spending one collected block |
| E | Craft one sword from three wood |

Grass drops dirt. Dirt, stone and wood keep separate counters. Placing requires a matching resource and an empty tile that does not overlap the player or an enemy. Crafting consumes exactly three wood and can succeed once. The sword icon appears in the HUD when crafted. Slime enemies patrol, turn at walls and ledges, and fall when their support is mined.

**Outcome:** a sandbox with resource collection, one crafting recipe and patrolling enemies. Combat, damage and enemy defeat are introduced in Workshop 4. Enemy contact does not hurt the player. Restarting resets the world and collected resources.

## Two-hour teaching agenda

| Minutes | Activity |
| --- | --- |
| 0–10 | Run the Workshop 2 checkpoint and recap mining and placement |
| 10–35 | Collect dirt and display a counter; generalise to stone and wood |
| 35–55 | Select materials and spend one only after successful placement |
| 55–75 | Add the three-wood sword recipe and show its crafted state |
| 75–105 | Introduce an Enemy class, render it and implement patrol movement |
| 105–120 | Test insufficient resources, crafting, placement and enemy boundaries |

Use the bundled fixed map, artwork and rendering code to keep the session focused. Teach one material through its complete collect/place flow before reusing the pattern for other materials. The world contains several instances of the same enemy type.

## Independent work before Workshop 4

- Finish stone and wood counters if only dirt was completed in class.
- Mine three wood, craft a sword, and explain why crafting cannot repeat.
- Test placement with zero stock, outside reach and on occupied tiles.
- Observe enemies beside a wall or a mined ledge. Plan where an attack range and health state would fit into the classes.

## Code map

- `SandboxGame`: JavaFX setup, input, loop, camera, resource/crafting integration and rendering.
- `World`: fixed tile map, mining, placement and terrain collision.
- `Player`: horizontal movement, gravity, grounded jumping and collision.
- `Inventory`: three counters and the single sword recipe.
- `Enemy`: patrol movement and gravity.
- `Collision`: rectangle overlap used to reject occupied placements.
- `GameConfig`, `GameArt`: constants and bundled asset loading.

This folder is self-contained and can be copied or zipped independently for the Academy course use described below. See [asset provenance](ASSET_LICENSE.md).

## Course use

This is KCL Tech Academy teaching material for course participants and facilitators. It is not an open-source project and does not accept external contributions. The [course licensing notice](https://github.com/WMK15/kcltech-javagame-complete/blob/main/LICENSING.md) applies to the code and teaching materials; a copy is included in workshop ZIPs. The separate CC0 artwork terms remain in [ASSET_LICENSE.md](ASSET_LICENSE.md).
