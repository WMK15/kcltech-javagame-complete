# KCL Tech Academy: Java Game Development

Four two-hour workshops build a playable **2D Minecraft-inspired sandbox** with **Java 21, JavaFX and IntelliJ IDEA**. Each workshop folder is an independent Maven project with its own source, tests, bundled artwork, asset licence and setup instructions.

## Choose a workshop

These are cumulative **end-of-workshop reference checkpoints**. Students continue their own projects between sessions and can use a checkpoint as a working example or recovery point. Workshop 4 is the complete game.

| Workshop | Project and session guide | Available mechanics |
| --- | --- | --- |
| 1 | [IntelliJ IDEA, world and movement](workshops/workshop-01/README.md) | JavaFX window, tile world, game loop and horizontal movement |
| 2 | [Physics, collision and interaction](workshops/workshop-02/README.md) | Adds gravity, jumping, collision, mining and unlimited dirt placement |
| 3 | [Resources, crafting and enemies](workshops/workshop-03/README.md) | Adds dirt/stone/wood counters, material costs, one sword recipe and enemy patrols |
| 4 | [Combat, finalisation and presentations](workshops/workshop-04/README.md) | Adds attacks, enemy health, damage, defeat and completion feedback |

Each guide includes controls, a two-hour agenda, a checkpoint and independent work. Workshop 4 uses its first 90 minutes for building, integration and fixes; **minutes 90-120 are reserved for student presentations**.

## Run in IntelliJ IDEA

1. Install **JDK 21** and IntelliJ IDEA.
2. Open the chosen workshop folder, such as `workshops/workshop-01`, as a **Maven project**.
3. Select JDK 21 as the Project SDK and Maven runner JRE, then reload Maven dependencies.
4. In that project's Maven tool window, run **Plugins → javafx → javafx:run**.

From the repository root, launch a specific version with:

```sh
# Replace 04 with 01, 02 or 03 to run an earlier workshop.
mvn --file workshops/workshop-04/pom.xml javafx:run
```

JavaFX is supplied through Maven; no manual JavaFX SDK download is needed. The game requires a graphical desktop. The root `pom.xml` builds all four projects; launch JavaFX against an individual workshop's `pom.xml`.

## Build and test

From the repository root:

```sh
# Compile, test and package all four versions.
mvn verify

# Test one workshop independently.
mvn --file workshops/workshop-02/pom.xml test
```

GitHub Actions runs the same root build with JDK 21. Tests exercise the game models without opening JavaFX windows.

## Distribute the projects

Run `./scripts/package-workshops.ps1` in PowerShell to generate four standalone ZIP files in `target/workshop-archives/`. Each archive includes source, tests, all 12 PNG assets, its asset licence and IntelliJ instructions. Build outputs and IDE settings are excluded. Recipients need JDK 21 and IntelliJ IDEA with Maven; the first import downloads dependencies.

Each workshop retains its own `ASSET_LICENSE.md` with the CC0 artwork's provenance, so copied or extracted projects keep their licence information.

## Teaching scope

Workshop 1 uses level terrain and horizontal movement. Workshop 2 introduces physics and editable terrain; right click places unlimited dirt. Workshop 3 introduces collected resources and placement costs: grass drops dirt, materials retain their identity, and three wood crafts one sword. Workshop 4 connects that sword to combat. The completed game has one enemy type, with several instances, and one crafting recipe.

There are 7.5 hours before presentations, including setup and debugging. Preflight the development environment where possible and supply assets and rendering boilerplate. Teach one material through the collect/place flow before generalising it to stone and wood. Keep resources as HUD counters and use independent work between sessions to finish the exercises.

Procedural terrain, extra recipes, advanced enemy AI, an inventory screen, multiplayer and save/load remain optional extensions.

## Troubleshooting

- **Java version error:** select JDK 21 for both the Project SDK and Maven runner.
- **Missing JavaFX classes:** reload Maven and launch using the chosen workshop's `javafx:run` goal.
- **No game launches from the root build:** choose an individual workshop project; `mvn verify` builds and tests all four.
- **No display/headless environment:** run the game on a graphical desktop; the tests do not need one.
