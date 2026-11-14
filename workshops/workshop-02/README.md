# Workshop 2: Physics, Collision and World Interaction

This is the runnable **end-of-Workshop-2 checkpoint** for KCL Tech Academy: Java Game Development. It includes Workshop 1's JavaFX game loop, tile map and horizontal movement, then adds gravity, jumping, terrain collision, mining and block placement. The project runs independently of the other workshop folders.

## Run in IntelliJ IDEA

1. Install JDK 21 and IntelliJ IDEA.
2. Open **this folder** (the one containing this README and `pom.xml`) as a Maven project.
3. Select JDK 21 as the Project SDK and Maven runner JRE, then reload Maven dependencies.
4. In the Maven tool window, run **Plugins → javafx → javafx:run**.

From a terminal in this folder, use `mvn clean javafx:run`. JavaFX dependencies and artwork are supplied with the project; the game needs a graphical desktop. Run the automated model tests with `mvn test`.

## Controls and checkpoint

| Input | Action |
| --- | --- |
| A / D or left / right arrows | Move horizontally |
| Space / W / up arrow | Jump when standing on terrain |
| Left mouse click | Break a block within four tiles of the player |
| Right mouse click | Place dirt in an empty tile within four tiles |

Placement cannot cover the player. Dirt is unlimited in this checkpoint; mining changes the map without collecting items. Workshop 3 introduces material counters and placement costs. Wood piles are ordinary solid terrain here: jump over or mine through them.

**Outcome:** A playable sandbox where students move, jump, collide with terrain and modify their environment. Reopening the game restores the fixed map.

## Two-hour session

| Minutes | Activity |
| --- | --- |
| 0–10 | Run Workshop 1 and review update versus render |
| 10–35 | Add vertical velocity, gravity and grounded jumping |
| 35–65 | Detect terrain collision and resolve each movement axis |
| 65–85 | Convert mouse coordinates to world tiles and mine blocks |
| 85–105 | Place dirt, enforce reach and reject player overlap |
| 105–120 | Test walls, jumps, edges and mining the ground below the player |

Use the Workshop 1 checkpoint as the starting reference and this project as the completed example. `Player` owns physics, `World` owns tiles and terrain collision, `Collision` compares rectangles, and `SandboxGame` connects keyboard/mouse events to the game loop and renderer. `GameArt` loads the supplied textures. The shared art pack also includes unused assets for later workshops; see [the asset licence](ASSET_LICENSE.md).

## Independent work before Workshop 3

- Mine a small staircase, jump onto it and rebuild part of the terrain.
- Test placement beside the player, inside the player, beyond reach and outside the map.
- Explain the difference between pixel coordinates, camera coordinates and tile indices.
- Decide which information a resource counter must preserve when a block is mined.

Compare your project with `../workshop-03` after attempting resource collection in the next session.

## Course use

This is KCL Tech Academy teaching material for course participants and facilitators. It is not an open-source project and does not accept external contributions. The [course licensing notice](https://github.com/WMK15/kcltech-javagame-complete/blob/main/LICENSING.md) applies to the code and teaching materials; a copy is included in workshop ZIPs. The separate CC0 artwork terms remain in [ASSET_LICENSE.md](ASSET_LICENSE.md).
