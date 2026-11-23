
package bombastic.game;

import bombastic.game.entities.Bomb;
import bombastic.game.entities.DestructibleWall;
import bombastic.game.entities.Explosion;
import bombastic.game.entities.Player;
import bombastic.game.entities.powerups.Capacity;
import bombastic.game.entities.powerups.Life;
import bombastic.game.entities.powerups.PowerUp;
import bombastic.game.entities.powerups.Range;
import bombastic.game.entities.powerups.Speed;
import java.util.ArrayList;
import java.util.Random;

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
    private final ArrayList<Explosion> explosions =
        new ArrayList<>();
    private final ArrayList<PowerUp> powerUps =
        new ArrayList<>();
    private final ArrayList<DestructibleWall> destructibleWalls =
        new ArrayList<>();

    private final Random random = new Random();

    private final CollisionDetection collisionDetection =
        new CollisionDetection();

    private int nextExplosionId = 0;
    private boolean gameOver = false;

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

                    destructibleWalls.add(
                        new DestructibleWall(
                            offsetX() + col * TILE_SIZE,
                            offsetY() + row * TILE_SIZE,
                            TILE_SIZE
                        )
                    );
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

    public ArrayList<Explosion> getExplosions() {
        return explosions;
    }

    public ArrayList<PowerUp> getPowerUps() {
        return powerUps;
    }

    public ArrayList<DestructibleWall> getDestructibleWalls() {
        return destructibleWalls;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public String getWinner() {
        if (!gameOver) {
            return "";
        }

        if (player1.isDead() && player2.isDead()) {
            return "DRAW!";
        }

        if (player1.isDead()) {
            return "PLAYER 2 WINS!";
        }

        return "PLAYER 1 WINS!";
    }

    private double offsetX() {
        return (WIDTH - COLUMNS * TILE_SIZE) / 2.0;
    }

    private double offsetY() {
        return (HEIGHT - ROWS * TILE_SIZE) / 2.0;
    }

    public void placeBomb(Player player) {
        if (gameOver || player.isDead()) {
            return;
        }

        int activeBombs = 0;

        for (Bomb bomb : bombs) {
            if (bomb.getOwner() == player) {
                activeBombs++;
            }
        }

        if (activeBombs >= player.getMaxBombs()) {
            return;
        }

        double centerX = player.getX() + TILE_SIZE / 2.0;
        double centerY = player.getY() + TILE_SIZE / 2.0;

        int col = (int) Math.floor(
            (centerX - offsetX()) / TILE_SIZE
        );

        int row = (int) Math.floor(
            (centerY - offsetY()) / TILE_SIZE
        );

        if (row < 0 || row >= ROWS ||
            col < 0 || col >= COLUMNS ||
            arena[row][col] != 0) {
            return;
        }

        double x = offsetX() + col * TILE_SIZE;
        double y = offsetY() + row * TILE_SIZE;

        for (Bomb bomb : bombs) {
            if (bomb.getX() == x && bomb.getY() == y) {
                return;
            }
        }

        bombs.add(new Bomb(x, y, TILE_SIZE, player));
    }

    private CollisionSnapshot createCollisionSnapshot() {
        ArrayList<double[]> bombData = new ArrayList<>();

        for (Bomb bomb : bombs) {
            int ownerNumber =
                bomb.getOwner() == player1 ? 1 : 2;

            bombData.add(new double[] {
                bomb.getX(),
                bomb.getY(),
                ownerNumber,
                bomb.isOwnerCanPass() ? 1 : 0
            });
        }

        return new CollisionSnapshot(arena, bombData);
    }

    private void movePlayers(double dt) {
        Player p1 = player1;
        Player p2 = player2;

        p1.update(dt);
        p2.update(dt);

        double x1 = p1.getX();
        double y1 = p1.getY();
        double x2 = p2.getX();
        double y2 = p2.getY();

        double nextX1 = x1 + p1.getMovementX(dt);
        double nextY1 = y1 + p1.getMovementY(dt);
        double nextX2 = x2 + p2.getMovementX(dt);
        double nextY2 = y2 + p2.getMovementY(dt);

        CollisionSnapshot snapshot = createCollisionSnapshot();

        int size1 = p1.getCollisionSize();
        int size2 = p2.getCollisionSize();

        boolean[] xBlocked = collisionDetection.checkBoth(
            () -> snapshot.collides(nextX1, y1, size1, 1),
            () -> snapshot.collides(nextX2, y2, size2, 2)
        );

        if (!xBlocked[0]) {
            p1.setX(nextX1);
        }

        if (!xBlocked[1]) {
            p2.setX(nextX2);
        }

        double currentX1 = p1.getX();
        double currentX2 = p2.getX();

        boolean[] yBlocked = collisionDetection.checkBoth(
            () -> snapshot.collides(currentX1, nextY1, size1, 1),
            () -> snapshot.collides(currentX2, nextY2, size2, 2)
        );

        if (!yBlocked[0]) {
            p1.setY(nextY1);
        }

        if (!yBlocked[1]) {
            p2.setY(nextY2);
        }
    }

    private void addExplosion(int row, int col, int groupId) {
        double x = offsetX() + col * TILE_SIZE;
        double y = offsetY() + row * TILE_SIZE;

        Explosion explosion = new Explosion(
            x, y, TILE_SIZE, groupId
        );

        explosions.add(explosion);

        for (Bomb bomb : bombs) {
            if (bomb.getX() == x && bomb.getY() == y) {
                bomb.interact(explosion);
            }
        }
    }

    private void spawnPowerUp(int row, int col) {
        if (random.nextInt(100) >= 35) {
            return;
        }

        double x = offsetX() + col * TILE_SIZE;
        double y = offsetY() + row * TILE_SIZE;

        int type = random.nextInt(4);

        if (type == 0) {
            powerUps.add(new Capacity(x, y, TILE_SIZE));
        } else if (type == 1) {
            powerUps.add(new Range(x, y, TILE_SIZE));
        } else if (type == 2) {
            powerUps.add(new Speed(x, y, TILE_SIZE));
        } else {
            powerUps.add(new Life(x, y, TILE_SIZE));
        }
    }

    private void spreadExplosion(
        int row, int col, int dr, int dc,
        int range, int groupId
    ) {
        for (int distance = 1; distance <= range; distance++) {
            int nextRow = row + dr * distance;
            int nextCol = col + dc * distance;

            if (nextRow < 0 || nextRow >= ROWS ||
                nextCol < 0 || nextCol >= COLUMNS) {
                break;
            }

            int tile = arena[nextRow][nextCol];

            if (tile == 1) {
                break;
            }

            addExplosion(nextRow, nextCol, groupId);

            if (tile == 2) {
                double x = offsetX() + nextCol * TILE_SIZE;
                double y = offsetY() + nextRow * TILE_SIZE;

                Explosion explosion =
                    explosions.get(explosions.size() - 1);

                for (DestructibleWall wall : destructibleWalls) {
                    if (wall.getX() == x && wall.getY() == y) {
                        wall.interact(explosion);
                        break;
                    }
                }

                arena[nextRow][nextCol] = 0;
                spawnPowerUp(nextRow, nextCol);
                break;
            }
        }
    }

    private void detonateBomb(Bomb bomb) {
        int col = (int) Math.floor(
            (bomb.getX() - offsetX()) / TILE_SIZE
        );

        int row = (int) Math.floor(
            (bomb.getY() - offsetY()) / TILE_SIZE
        );

        int groupId = ++nextExplosionId;
        int range = bomb.getRange();

        addExplosion(row, col, groupId);

        spreadExplosion(row, col, -1, 0, range, groupId);
        spreadExplosion(row, col, 1, 0, range, groupId);
        spreadExplosion(row, col, 0, -1, range, groupId);
        spreadExplosion(row, col, 0, 1, range, groupId);
    }

    private boolean overlaps(
        double x1, double y1, double size1,
        double x2, double y2, double size2
    ) {
        return x1 < x2 + size2 &&
               x1 + size1 > x2 &&
               y1 < y2 + size2 &&
               y1 + size1 > y2;
    }

    private void damagePlayer(Player player) {
        if (player.isDead()) {
            return;
        }

        int size = player.getCollisionSize();
        double margin = (TILE_SIZE - size) / 2.0;

        double x = player.getX() + margin;
        double y = player.getY() + margin;

        for (Explosion explosion : explosions) {
            if (overlaps(
                x, y, size,
                explosion.getX(), explosion.getY(),
                explosion.getSize()
            )) {
                player.interact(explosion);
            }
        }
    }

    private void collectPowerUps(Player player) {
        if (player.isDead()) {
            return;
        }

        int size = player.getCollisionSize();
        double margin = (TILE_SIZE - size) / 2.0;

        double x = player.getX() + margin;
        double y = player.getY() + margin;

        for (int i = powerUps.size() - 1; i >= 0; i--) {
            PowerUp powerUp = powerUps.get(i);

            if (overlaps(
                x, y, size,
                powerUp.getX(), powerUp.getY(),
                powerUp.getSize()
            )) {
                powerUp.interact(player);
                powerUps.remove(i);
            }
        }
    }

    public void update(double dt) {
        if (gameOver) {
            return;
        }

        double elapsed = Math.min(dt, 0.1);
        double remaining = elapsed;

        while (remaining > 0) {
            double step = Math.min(remaining, 0.01);

            movePlayers(step);

            for (Bomb bomb : bombs) {
                bomb.updateOwnerPass();
            }

            remaining -= step;
        }

        for (int i = explosions.size() - 1; i >= 0; i--) {
            Explosion explosion = explosions.get(i);
            explosion.update(elapsed);

            if (explosion.isFinished()) {
                explosions.remove(i);
            }
        }

        for (Bomb bomb : bombs) {
            bomb.update(elapsed);
        }

        boolean foundExplosion = true;

        while (foundExplosion) {
            foundExplosion = false;

            for (int i = 0; i < bombs.size(); i++) {
                Bomb bomb = bombs.get(i);

                if (bomb.isExploded()) {
                    bombs.remove(i);
                    detonateBomb(bomb);
                    foundExplosion = true;
                    break;
                }
            }
        }

        for (int i = destructibleWalls.size() - 1; i >= 0; i--) {
            if (destructibleWalls.get(i).isDestroyed()) {
                destructibleWalls.remove(i);
            }
        }

        damagePlayer(player1);
        damagePlayer(player2);

        collectPowerUps(player1);
        collectPowerUps(player2);

        if (player1.isDead() || player2.isDead()) {
            gameOver = true;
        }
    }

    public void shutdown() {
        collisionDetection.shutdown();
    }
}
