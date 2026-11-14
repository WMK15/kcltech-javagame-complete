# KCL Tech Academy: Java Game Development

A playable **2D Minecraft-inspired sandbox** built with **Java 21 + JavaFX**, for four two-hour teaching workshops. It uses a bundled CC0 pixel-art asset pack, with no separate downloads or game engine required.

## Four runnable versions

Each folder is an independent Maven project with its own source, tests, assets and README. Open the folder for the session in IntelliJ IDEA. These are cumulative **end-of-workshop reference checkpoints**; students can continue their own projects between sessions.

| Version | Project | Available mechanics |
| --- | --- | --- |
| Workshop 1 | [World and movement](workshops/workshop-01/README.md) | JavaFX window, tile world, game loop and horizontal player movement |
| Workshop 2 | [Physics and interaction](workshops/workshop-02/README.md) | Adds gravity, jumping, collision, mining and unlimited dirt placement |
| Workshop 3 | [Resources, crafting and enemies](workshops/workshop-03/README.md) | Adds dirt/stone/wood collection, material costs, one sword recipe and enemy patrols |
| Workshop 4 | [Combat and final game](workshops/workshop-04/README.md) | Adds attacks, enemy health, damage, defeat and completion feedback |

Earlier versions contain only the systems introduced so far. Workshop 2 uses free dirt placement so that resource management can be introduced in Workshop 3. Each README includes a two-hour agenda, controls, a checkpoint and independent work. Workshop 4 reserves minutes **90-120 for student presentations**.

The root project remains the complete instructor/reference game. To test all four versions together, run `mvn --file workshops/pom.xml verify`. To run a particular version from the repository root, use, for example, `mvn --file workshops/workshop-02/pom.xml javafx:run`.

For four self-contained ZIP files, run `./scripts/package-workshops.ps1` in PowerShell. The archives are written to `target/workshop-archives/`; they contain source, tests, assets and setup instructions, without build outputs or IntelliJ settings. Recipients need JDK 21 and IntelliJ IDEA with Maven; the first Maven import downloads dependencies.

## Run in IntelliJ IDEA

1. Install **JDK 21** and IntelliJ IDEA.
2. Open this repository as a **Maven project** and select JDK 21 for the project SDK.
3. Let IntelliJ import Maven dependencies.
4. In IntelliJ's Maven tool window, run **Plugins → javafx → javafx:run**. Alternatively use the terminal:

```bash
mvn clean javafx:run
```

JavaFX is provided via Maven; no separate manual JavaFX SDK download is required. A desktop session with graphical display is required to run the game.

Run tests with `mvn test`.

## Included graphics

All 12 PNG assets live in `src/main/resources/assets/` and are loaded by `GameArt`: terrain, sky, two player animation frames, enemy, sword and additional tile textures. See [asset catalogue and licence](ASSET_LICENSE.md).

## Controls

| Input | Action |
| --- | --- |
| A / D or ← / → | Move |
| Space / W / ↑ | Jump |
| Left mouse click | Mine a block within four tiles |
| 1 / 2 / 3 (or numpad) | Select dirt / stone / wood to place |
| Right mouse click | Place the selected block within four tiles (costs one of that material) |
| E | Craft a sword from three wood |
| F | Attack an enemy in front of you |

**Goal:** Mine three wood blocks, craft a sword and defeat the six red enemies spread across the world. The HUD shows your material counts, selected block and enemies remaining. You can also rebuild the world by placing collected blocks.

The fixed map contains grass over dirt, stone deeper down, and small wood piles above ground. Grass drops dirt; dirt, stone and wood can all be mined and placed. There are three resource counters and one recipe.

To add more enemies, increase `ENEMY_COUNT` in `src/main/java/uk/ac/kcltech/sandbox/GameConfig.java` and restart the game. Enemies are automatically spread across the map.

## Four workshops

| Workshop | Subjects | Result |
| --- | --- | --- |
| [1: Setup, game loop, world and movement](docs/workshop-01.md) | IntelliJ migration; JavaFX Stage/Scene/Canvas; AnimationTimer; world tiles and keyboard input | A controllable player in a 2D world |
| [2: Physics and world interaction](docs/workshop-02.md) | Gravity, jumping, collision, mining and placing | Interactive sandbox |
| [3: Resources, crafting and enemies](docs/workshop-03.md) | Resource collection, one recipe, enemy patrol | Resources and enemies |
| [4: Combat and presentations](docs/workshop-04.md) | Attacks, damage, integration, debugging; final 30-minute showcase | Playable finished game |

The root contains the **complete instructor/reference implementation**. The separate [workshop projects](workshops/README.md) provide runnable versions of the game at each checkpoint. Use the corresponding workshop document and project as a reference or recovery point, and introduce the relevant classes and methods one session at a time.

## Code map

- `GameArt`: pixel-art resource loading.
- `SandboxGame`: JavaFX window, game loop, input handling, rendering, HUD and integration.
- `World`: tile grid, mining, placing and tile collision.
- `Player`: movement, gravity, jumping and collision.
- `Enemy`: simple patrol, falling and health.
- `Inventory`: dirt, stone and wood counters and one sword recipe.
- `Collision`: rectangle overlap helper.
- `GameConfig`: shared dimensions and physics constants.

### Scope

Core: tile map, movement, gravity, jumping, collision, mining, placing, three resources, one crafting recipe, one enemy type and combat. No procedural generation, multiplayer, save/load or extra inventory UI is implemented.

## Teaching note

IntelliJ and Maven setup must be completed in Workshop 1. Students should start from an instructor-prepared skeleton or reconstruct code progressively. Workshop exercises are in `docs/`, and runnable checkpoints with session agendas are in `workshops/`.

There are 7.5 hours before the final presentations, including setup and debugging. Preflight JavaFX/IntelliJ setup where possible and provide assets and rendering boilerplate. Teach dirt collection first; stone and wood reuse the same mining, counting and placement pattern and can be introduced as guided independent work. Keep Workshop 4's final 30 minutes for presentations, and avoid adding inventory screens or further recipes to the required work.

## Troubleshooting

- **Java version error:** set IntelliJ's Project SDK and Maven runner JRE to JDK 21.
- **Missing JavaFX classes:** reload Maven dependencies and use `mvn javafx:run`, rather than a plain Java run configuration.
- **No display/headless CI:** tests do not launch a JavaFX window; the game needs a graphical desktop.
