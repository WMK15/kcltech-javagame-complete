# Workshop 3: Resources, crafting and enemies (2 hours)

## Topics
- Encapsulating collected resources
- Conditionals and state changes for one crafting recipe
- Enemy classes and basic patrol movement

## Build in class
1. Start with a dirt counter, then track dirt, stone and wood separately. Grass drops dirt.
2. Select dirt, stone or wood with 1, 2 or 3; place that material and spend one matching block.
3. Craft one sword from three wood when E is pressed.
4. Create one enemy with health and simple back-and-forth patrol.
5. Draw the enemy and the inventory status.

## Checkpoint
Collected materials keep their identity when placed, three wood makes a sword, and one enemy type patrols the world. The reference game spawns several instances of that type.

## Reference files
`Inventory.java`, `Enemy.java`, plus inventory/keyboard/rendering sections of `SandboxGame.java`.

## Independent work
Extend the dirt-counter example to stone and wood using the same pattern if unfinished in class. Prepare to integrate an attack action and test enemies near blocks.

## Keep within two hours

Use the supplied fixed terrain and textures. Teach one resource through the complete collect/place loop before generalising it. Keep the three counters visible as text; a full inventory screen and additional recipes remain optional.
