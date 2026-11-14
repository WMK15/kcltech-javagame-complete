package uk.ac.kcltech.sandbox;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.HashSet;
import java.util.Set;

import static uk.ac.kcltech.sandbox.GameConfig.*;

/** End of Workshop 2: physics, collision and changing the tile world. */
public final class SandboxGame extends Application {
    private final World world = new World();
    private final GameArt art = new GameArt();
    private final Player player = new Player();
    private final Set<KeyCode> pressed = new HashSet<>();
    private double cameraX;
    private double elapsed;
    private double lastRenderedPlayerX = Double.NaN;
    private String status = "Jump onto the wood piles, mine a path, then build with dirt.";
    private double statusSeconds = 5;

    @Override
    public void start(Stage stage) {
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        Scene scene = new Scene(new StackPane(canvas));
        scene.setOnKeyPressed(event -> pressed.add(event.getCode()));
        scene.setOnKeyReleased(event -> pressed.remove(event.getCode()));
        scene.setOnMousePressed(event -> {
            int column = (int) Math.floor((event.getX() + cameraX) / TILE);
            int row = (int) Math.floor(event.getY() / TILE);
            double targetX = column * TILE + TILE / 2.0;
            double targetY = row * TILE + TILE / 2.0;
            double playerCenterX = player.x() + Player.WIDTH / 2;
            double playerCenterY = player.y() + Player.HEIGHT / 2;
            if (Math.hypot(targetX - playerCenterX, targetY - playerCenterY) > TILE * 4) {
                status = "That block is out of reach.";
                statusSeconds = 3;
                return;
            }
            if (event.getButton() == MouseButton.PRIMARY) {
                world.mine(column, row);
            } else if (event.getButton() == MouseButton.SECONDARY) {
                boolean overlapsPlayer = Collision.overlaps(column * TILE, row * TILE, TILE, TILE,
                        player.x(), player.y(), Player.WIDTH, Player.HEIGHT);
                if (!overlapsPlayer) world.place(column, row, World.Tile.DIRT);
            }
        });
        stage.focusedProperty().addListener((observable, oldValue, focused) -> {
            if (!focused) pressed.clear();
        });
        stage.setTitle("KCL Tech Academy | Workshop 2: Physics and World Interaction");
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

    private void update(double dt) {
        boolean left = pressed.contains(KeyCode.A) || pressed.contains(KeyCode.LEFT);
        boolean right = pressed.contains(KeyCode.D) || pressed.contains(KeyCode.RIGHT);
        boolean jump = pressed.contains(KeyCode.SPACE) || pressed.contains(KeyCode.W) || pressed.contains(KeyCode.UP);
        player.update(world, dt, left, right, jump);
        elapsed += dt;
        statusSeconds = Math.max(0, statusSeconds - dt);
        cameraX = Math.max(0, Math.min(COLUMNS * TILE - WIDTH,
                player.x() + Player.WIDTH / 2 - WIDTH / 2.0));
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

        g.setFill(Color.web("#101827", 0.86));
        g.fillRoundRect(12, 12, WIDTH - 24, 72, 10, 10);
        g.setFill(Color.WHITE);
        g.setFont(Font.font("Monospaced", 15));
        g.fillText("Workshop 2 | A/D move | Space jump | Left click mine", 24, 38);
        g.fillText("Right click: place dirt within 4 tiles (unlimited this workshop)", 24, 65);
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
