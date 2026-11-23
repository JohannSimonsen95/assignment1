
package bombastic.game;

import bombastic.game.entities.Bomb;
import bombastic.game.entities.Player;
import java.util.ArrayList;

public class GameModel {

    public static final int TILE_SIZE = 40;
    public static final int COLUMNS = 15;
    public static final int ROWS = 11;
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    private final int[][] arena = new int[ROWS][COLUMNS];

    private final Player player1 = new Player(
        140, 120, TILE_SIZE, "blue_down_0.png"
    );

    private final Player player2 = new Player(
        620, 440, TILE_SIZE, "red_down_0.png"
    );

    private final ArrayList<Bomb> bombs = new ArrayList<>();

    public GameModel() {
        createArena();
    }

    private void createArena() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {

                boolean wall =
                    row == 0 ||
                    row == ROWS - 1 ||
                    col == 0 ||
                    col == COLUMNS - 1 ||
                    (row % 2 == 0 && col % 2 == 0);

                boolean crate =
                    !wall && (row + col) % 3 == 0;

                if ((row == 1 && col == 1) ||
                    (row == 1 && col == 2) ||
                    (row == 2 && col == 1) ||
                    (row == ROWS - 2 && col == COLUMNS - 2) ||
                    (row == ROWS - 2 && col == COLUMNS - 3) ||
                    (row == ROWS - 3 && col == COLUMNS - 2)) {
                    crate = false;
                }

                if (wall) {
                    arena[row][col] = 1;
                } else if (crate) {
                    arena[row][col] = 2;
                } else {
                    arena[row][col] = 0;
                }
            }
        }
    }

    public int getTile(int row, int col) {
        return arena[row][col];
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public ArrayList<Bomb> getBombs() {
        return bombs;
    }

    public void placeBomb(Player player) {
        int activeBombs = 0;

        for (Bomb bomb : bombs) {
            if (bomb.getOwner() == player) {
                activeBombs++;
            }
        }

        if (activeBombs >= 1) {
            return;
        }

        double offsetX =
            (WIDTH - COLUMNS * TILE_SIZE) / 2.0;

        double offsetY =
            (HEIGHT - ROWS * TILE_SIZE) / 2.0;

        double centerX = player.getX() + TILE_SIZE / 2.0;
        double centerY = player.getY() + TILE_SIZE / 2.0;

        int col = (int) Math.floor(
            (centerX - offsetX) / TILE_SIZE
        );

        int row = (int) Math.floor(
            (centerY - offsetY) / TILE_SIZE
        );

        if (row < 0 || row >= ROWS ||
            col < 0 || col >= COLUMNS) {
            return;
        }

        if (arena[row][col] != 0) {
            return;
        }

        double x = offsetX + col * TILE_SIZE;
        double y = offsetY + row * TILE_SIZE;

        for (Bomb bomb : bombs) {
            if (bomb.getX() == x && bomb.getY() == y) {
                return;
            }
        }

        bombs.add(new Bomb(x, y, TILE_SIZE, player));
    }

    private boolean isBlocked(double x, double y) {
        double offsetX =
            (WIDTH - COLUMNS * TILE_SIZE) / 2.0;

        double offsetY =
            (HEIGHT - ROWS * TILE_SIZE) / 2.0;

        int col = (int) Math.floor((x - offsetX) / TILE_SIZE);
        int row = (int) Math.floor((y - offsetY) / TILE_SIZE);

        if (row < 0 || row >= ROWS ||
            col < 0 || col >= COLUMNS) {
            return true;
        }

        return arena[row][col] != 0;
    }

    private boolean collides(double x, double y, int size) {
        double margin = (TILE_SIZE - size) / 2.0;
        double edge = size - 0.001;

        double left = x + margin;
        double top = y + margin;

        return isBlocked(left, top)
            || isBlocked(left + edge, top)
            || isBlocked(left, top + edge)
            || isBlocked(left + edge, top + edge);
    }

    public void update(double dt) {
        double remaining = Math.min(dt, 0.1);

        while (remaining > 0) {
            double step = Math.min(remaining, 0.01);

            movePlayer(player1, step);
            movePlayer(player2, step);

            remaining -= step;
        }

        for (int i = bombs.size() - 1; i >= 0; i--) {
            Bomb bomb = bombs.get(i);

            bomb.update(Math.min(dt, 0.1));

            if (bomb.isExploded()) {
                bombs.remove(i);
            }
        }
    }

    private void movePlayer(Player player, double dt) {
        double oldX = player.getX();
        double oldY = player.getY();

        player.update(dt);

        double newX = player.getX();
        double newY = player.getY();

        if (collides(newX, oldY, player.getCollisionSize())) {
            player.setX(oldX);
        }

        if (collides(player.getX(), newY, player.getCollisionSize())) {
            player.setY(oldY);
        }
    }
}
