# Workshop 1: IntelliJ IDEA, Game Fundamentals, World and Movement

This standalone project is the completed checkpoint for the first two-hour KCL Tech Academy: Java Game Development workshop. It shows a textured 2D tile world and a player who moves horizontally. The player's height stays fixed for this session.

## Open and run in IntelliJ IDEA

1. Install JDK 21 or later and IntelliJ IDEA.
2. Open **this folder's `pom.xml`** as a Maven project in IntelliJ IDEA.
3. Select JDK 21 or later as the Project SDK and Maven runner JRE.
4. Wait for Maven to import the dependencies.
5. In the Maven tool window, run **Plugins → javafx → javafx:run**.

With Maven installed, you can also run these commands from this folder:

```bash
mvn clean javafx:run
mvn test
```

Maven downloads JavaFX; a separate JavaFX SDK is unnecessary. The game window needs a graphical desktop. All textures are included in `src/main/resources/assets/`; see [ASSET_LICENSE.md](ASSET_LICENSE.md).

## Controls and checkpoint

| Input | Action |
| --- | --- |
| A / D or Left / Right arrows | Move horizontally |

Run the project, move in both directions, and continue right until the camera scrolls. The player stays inside the map. Releasing the controls stops movement.

**Outcome:** A working JavaFX project in IntelliJ IDEA with a simple 2D world and a controllable player.

## Suggested two-hour session

| Time | Activity |
| --- | --- |
| 0–20 minutes | Transition to IntelliJ IDEA, choose the JDK, import Maven and run a JavaFX window |
| 20–40 minutes | Explain Stage, Scene, Canvas and GraphicsContext; draw the sky and tiles |
| 40–65 minutes | Build the two-dimensional tile array and render its rows and columns |
| 65–85 minutes | Represent the player with a class, fields and methods |
| 85–110 minutes | Read keyboard input and update movement through AnimationTimer using elapsed seconds |
| 110–120 minutes | Check the game, discuss update versus render and set independent work |

The textures, resource loader, camera and player animation can be supplied as supporting code. Focus the live exercises on the window, tile grid, player object and horizontal game loop.

## Code map

- `SandboxGame`: window setup, keyboard state, AnimationTimer, update and Canvas drawing.
- `World`: fixed row/column tile array with grass, dirt and stone.
- `Player`: position, direction and horizontal movement.
- `GameConfig`: window dimensions, tile dimensions and movement speed.
- `GameArt`: loading bundled textures.

This is an end-of-session reference, so students can compare it with their own implementation after completing the exercises. Open only one workshop project at a time: the stages use the same Java package names.

## Independent work before Workshop 2

1. Explain how `dt` changes a speed in pixels per second into movement for one frame.
2. Change the player's speed and one terrain texture, then describe the result.
3. Trace one tile from `tiles[row][column]` to its screen position.
4. Identify where vertical velocity, gravity and a jump action could fit in the next workshop.

## Troubleshooting

- If JavaFX classes are missing, reload Maven and launch with `javafx:run`.
- If compilation reports an unsupported Java release, select JDK 21 or later for both the project and Maven runner.
- If no game window opens in a headless environment, run on a graphical desktop. Unit tests exercise movement and the world without opening JavaFX.

## Course use

This is KCL Tech Academy teaching material for course participants and facilitators. It is not an open-source project and does not accept external contributions. The [course licensing notice](https://github.com/WMK15/kcltech-javagame-complete/blob/main/LICENSING.md) applies to the code and teaching materials; a copy is included in workshop ZIPs. The separate CC0 artwork terms remain in [ASSET_LICENSE.md](ASSET_LICENSE.md).
