package uk.ac.kcltech.sandbox;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static uk.ac.kcltech.sandbox.GameConfig.*;

/** Complete reference implementation for the four-workshop Academy series. */
public final class SandboxGame extends Application {
    private final World world = new World();
    private final GameArt art = new GameArt();
    private double elapsed;
    private double lastRenderedPlayerX = Double.NaN;
    private final Player player = new Player();
    private final Inventory inventory = new Inventory();
    private final List<Enemy> enemies = new ArrayList<>();
    private final Set<KeyCode> pressed = new HashSet<>();
    private World.Tile selectedBlock = World.Tile.DIRT;
    private double cameraX;
    private String status = "Mine 3 wood blocks, press E to craft a sword. Use 1/2/3 to select blocks.";
    private double statusSeconds = 5;

    @Override
    public void start(Stage stage) {
        // Spread enemies across the map, leaving room near the player's spawn.
        for (int i = 0; i < ENEMY_COUNT; i++) {
            double column = 18 + i * (COLUMNS - 20.0) / ENEMY_COUNT;
            Enemy enemy = new Enemy(column * TILE);
            while (world.collides(enemy.x(), enemy.y(), Enemy.WIDTH, Enemy.HEIGHT)) {
                column++;
                enemy = new Enemy(column * TILE);
            }
            enemies.add(enemy);
        }
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        Scene scene = new Scene(new StackPane(canvas));
        scene.setOnKeyPressed(event -> {
            pressed.add(event.getCode());
            switch (event.getCode()) {
                case DIGIT1, NUMPAD1 -> selectedBlock = World.Tile.DIRT;
                case DIGIT2, NUMPAD2 -> selectedBlock = World.Tile.STONE;
                case DIGIT3, NUMPAD3 -> selectedBlock = World.Tile.WOOD;
                case E -> {
                    if (inventory.craftSword()) notifyPlayer("Crafted a sword! Press F near an enemy.");
                    else notifyPlayer("Crafting requires 3 wood and no existing sword.");
                }
                case F -> attack();
                default -> { }
            }
        });
        scene.setOnKeyReleased(event -> pressed.remove(event.getCode()));
        scene.setOnMousePressed(event -> {
            int column = (int) Math.floor((event.getX() + cameraX) / TILE);
            int row = (int) Math.floor(event.getY() / TILE);
            double targetX = column * TILE + TILE / 2.0;
            double targetY = row * TILE + TILE / 2.0;
            double playerCenterX = player.x() + Player.WIDTH / 2;
            double playerCenterY = player.y() + Player.HEIGHT / 2;
            if (Math.hypot(targetX - playerCenterX, targetY - playerCenterY) > TILE * 4) {
                notifyPlayer("That block is out of reach.");
                return;
            }
            if (event.getButton() == MouseButton.PRIMARY) {
                inventory.collect(world.mine(column, row));
            } else if (event.getButton() == MouseButton.SECONDARY) {
                if (inventory.count(selectedBlock) == 0) {
                    notifyPlayer("No " + selectedBlock + " blocks left. Mine some first.");
                    return;
                }
                boolean overlapsPlayer = Collision.overlaps(column * TILE, row * TILE, TILE, TILE,
                        player.x(), player.y(), Player.WIDTH, Player.HEIGHT);
                boolean overlapsEnemy = enemies.stream().anyMatch(enemy -> enemy.alive() && Collision.overlaps(
                        column * TILE, row * TILE, TILE, TILE, enemy.x(), enemy.y(), Enemy.WIDTH, Enemy.HEIGHT));
                if (!overlapsPlayer && !overlapsEnemy && world.place(column, row, selectedBlock)) {
                    inventory.spend(selectedBlock);
                }
            }
        });

        stage.setTitle("KCL Tech Academy | JavaFX Sandbox");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        GraphicsContext graphics = canvas.getGraphicsContext2D();
        new AnimationTimer() {
            private long lastFrame;
            @Override
            public void handle(long now) {
                if (lastFrame == 0) { lastFrame = now; return; }
                double dt = Math.min((now - lastFrame) / 1_000_000_000.0, 0.033);
                lastFrame = now;
                update(dt);
                render(graphics);
            }
        }.start();
    }

    private void notifyPlayer(String message) {
        status = message;
        statusSeconds = 3;
    }

    private void update(double dt) {
        boolean left = pressed.contains(KeyCode.A) || pressed.contains(KeyCode.LEFT);
        boolean right = pressed.contains(KeyCode.D) || pressed.contains(KeyCode.RIGHT);
        boolean jump = pressed.contains(KeyCode.SPACE) || pressed.contains(KeyCode.W) || pressed.contains(KeyCode.UP);
        player.update(world, dt, left, right, jump);
        elapsed += dt;
        for (Enemy enemy : enemies) {
            enemy.update(world, dt);
        }
        statusSeconds = Math.max(0, statusSeconds - dt);
        cameraX = Math.max(0, Math.min(COLUMNS * TILE - WIDTH,
                player.x() + Player.WIDTH / 2 - WIDTH / 2.0));
    }

    private void attack() {
        if (!player.canAttack()) return;
        player.attacked();
        double reachStart = player.facingRight() ? player.x() + Player.WIDTH : player.x() - 44;
        int defeated = 0;
        for (Enemy enemy : enemies) {
            if (enemy.alive() && Collision.overlaps(reachStart, player.y() - 4, 44, Player.HEIGHT + 8,
                    enemy.x(), enemy.y(), Enemy.WIDTH, Enemy.HEIGHT)) {
                enemy.damage(inventory.hasSword() ? 3 : 1);
                if (!enemy.alive()) defeated++;
            }
        }
        if (defeated > 0) {
            long remaining = remainingEnemies();
            notifyPlayer(remaining == 0 ? "All enemies defeated!" : "Enemy defeated! " + remaining + " remaining.");
        }
    }

    private long remainingEnemies() {
        return enemies.stream().filter(Enemy::alive).count();
    }

    private void render(GraphicsContext g) {
        g.setImageSmoothing(false);
        for (int screenY = 0; screenY < HEIGHT; screenY += TILE) {
            for (int screenX = 0; screenX < WIDTH; screenX += TILE) {
                g.drawImage(art.sky(), screenX, screenY, TILE, TILE);
            }
        }
        int firstColumn = Math.max(0, (int) Math.floor(cameraX / TILE));
        int lastColumn = Math.min(COLUMNS - 1, firstColumn + WIDTH / TILE + 1);
        for (int row = 0; row < ROWS; row++) {
            for (int column = firstColumn; column <= lastColumn; column++) {
                World.Tile tile = world.get(column, row);
                if (tile == World.Tile.AIR) continue;
                Image texture = switch (tile) {
                    case GRASS -> art.grass();
                    case DIRT -> art.dirt();
                    case STONE -> art.stone();
                    case WOOD -> art.wood();
                    case AIR -> throw new IllegalStateException("Air has no block texture.");
                };
                g.drawImage(texture, column * TILE - cameraX, row * TILE, TILE, TILE);
            }
        }

        boolean moving = Math.abs(player.x() - lastRenderedPlayerX) > 0.1;
        Image playerFrame = moving && (int) (elapsed * 7) % 2 == 0
                ? art.playerStep() : art.playerIdle();
        lastRenderedPlayerX = player.x();
        if (!player.facingRight()) {
            g.save();
            g.translate(2 * (player.x() - cameraX) + Player.WIDTH, 0);
            g.scale(-1, 1);
        }
        g.drawImage(playerFrame, player.x() - cameraX, player.y(), Player.WIDTH, Player.HEIGHT);
        if (!player.facingRight()) g.restore();

        for (Enemy enemy : enemies) {
            if (!enemy.alive()) continue;
            g.drawImage(art.enemy(), enemy.x() - cameraX, enemy.y(), Enemy.WIDTH, Enemy.HEIGHT);
            g.setFill(Color.web("#7f1829"));
            g.fillRect(enemy.x() - cameraX, enemy.y() - 8, Enemy.WIDTH * enemy.health() / 3.0, 4);
        }

        g.setFill(Color.web("#101827", 0.86));
        g.fillRoundRect(12, 12, WIDTH - 24, 100, 10, 10);
        if (inventory.hasSword()) {
            g.drawImage(art.sword(), WIDTH - 60, 22, 34, 34);
        }
        g.setFill(Color.WHITE);
        g.setFont(Font.font("Monospaced", 15));
        g.fillText("A/D move | Space jump | Left click mine | Right click place", 24, 38);
        g.fillText("E craft sword (3 wood) | F attack | Sword: " + (inventory.hasSword() ? "yes" : "no") +
                " | Enemies remaining: " + remainingEnemies(), 24, 65);
        g.fillText("1 Dirt: " + inventory.count(World.Tile.DIRT) +
                " | 2 Stone: " + inventory.count(World.Tile.STONE) +
                " | 3 Wood: " + inventory.count(World.Tile.WOOD) +
                " | Placing: " + selectedBlock, 24, 92);
        if (statusSeconds > 0) {
            g.setFill(Color.web("#101827", 0.80));
            g.fillRoundRect(12, HEIGHT - 44, WIDTH - 24, 30, 8, 8);
            g.setFill(Color.WHITE);
            g.fillText(status, 24, HEIGHT - 23);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
