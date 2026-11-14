# KCL Tech Academy: Java Game Development

A playable **2D Minecraft-inspired sandbox** built with **Java 21 + JavaFX**, for four two-hour teaching workshops. It uses a bundled CC0 pixel-art asset pack, with no separate downloads or game engine required.

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
| Right mouse click | Place a dirt block within four tiles (costs one dirt) |
| E | Craft a sword from three dirt |
| F | Attack an enemy in front of you |

**Goal:** Mine three dirt blocks, craft a sword, approach the red enemy and defeat it. You can also rebuild the world by placing collected blocks.

## Four workshops

| Workshop | Subjects | Result |
| --- | --- | --- |
| [1: Setup, game loop, world and movement](docs/workshop-01.md) | IntelliJ migration; JavaFX Stage/Scene/Canvas; AnimationTimer; world tiles and keyboard input | A controllable player in a 2D world |
| [2: Physics and world interaction](docs/workshop-02.md) | Gravity, jumping, collision, mining and placing | Interactive sandbox |
| [3: Resources, crafting and enemies](docs/workshop-03.md) | Resource collection, one recipe, enemy patrol | Resources and enemies |
| [4: Combat and presentations](docs/workshop-04.md) | Attacks, damage, integration, debugging; final 30-minute showcase | Playable finished game |

This repo contains the **complete instructor/reference implementation**. To teach incrementally, use the workshop documents to introduce the corresponding classes and methods one session at a time. Do not give students the completed source upfront if they are expected to implement the exercises.

## Code map

- `GameArt`: pixel-art resource loading.
- `SandboxGame`: JavaFX window, game loop, input handling, rendering, HUD and integration.
- `World`: tile grid, mining, placing and tile collision.
- `Player`: movement, gravity, jumping and collision.
- `Enemy`: simple patrol, falling and health.
- `Inventory`: dirt collection and one sword recipe.
- `Collision`: rectangle overlap helper.
- `GameConfig`: shared dimensions and physics constants.

### Scope

Core: tile map, movement, gravity, jumping, collision, mining, placing, resource collection, one crafting recipe, one enemy type and combat. No procedural generation, multiplayer, save/load or extra inventory UI is implemented.

## Teaching note

IntelliJ and Maven setup must be completed in Workshop 1. Students should start from an instructor-prepared skeleton or reconstruct code progressively. Workshop exercises and checkpoints are in `docs/`.

## Troubleshooting

- **Java version error:** set IntelliJ's Project SDK and Maven runner JRE to JDK 21.
- **Missing JavaFX classes:** reload Maven dependencies and use `mvn javafx:run`, rather than a plain Java run configuration.
- **No display/headless CI:** tests do not launch a JavaFX window; the game needs a graphical desktop.
