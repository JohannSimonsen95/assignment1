
package bombastic.game;

import java.util.ArrayList;

public class CollisionSnapshot {

    private final int[][] arena;
    private final ArrayList<double[]> bombs;

    public CollisionSnapshot(int[][] arena, ArrayList<double[]> bombs) {
        this.arena = new int[arena.length][];

        for (int row = 0; row < arena.length; row++) {
            this.arena[row] = arena[row].clone();
        }

        this.bombs = new ArrayList<>();

        for (double[] bomb : bombs) {
            this.bombs.add(bomb.clone());
        }
    }

    private boolean isBlocked(double x, double y) {
        double offsetX = (GameModel.WIDTH -
            GameModel.COLUMNS * GameModel.TILE_SIZE) / 2.0;

        double offsetY = (GameModel.HEIGHT -
            GameModel.ROWS * GameModel.TILE_SIZE) / 2.0;

        int col = (int) Math.floor(
            (x - offsetX) / GameModel.TILE_SIZE
        );

        int row = (int) Math.floor(
            (y - offsetY) / GameModel.TILE_SIZE
        );

        if (row < 0 || row >= arena.length ||
            col < 0 || col >= arena[0].length) {
            return true;
        }

        return arena[row][col] != 0;
    }

    public boolean collides(
        double x, double y, int collisionSize,
        int playerNumber
    ) {
        double margin =
            (GameModel.TILE_SIZE - collisionSize) / 2.0;

        double left = x + margin;
        double top = y + margin;
        double edge = collisionSize - 0.001;

        if (isBlocked(left, top) ||
            isBlocked(left + edge, top) ||
            isBlocked(left, top + edge) ||
            isBlocked(left + edge, top + edge)) {
            return true;
        }

        for (double[] bomb : bombs) {
            double bombX = bomb[0];
            double bombY = bomb[1];
            int owner = (int) bomb[2];
            boolean ownerCanPass = bomb[3] == 1;

            if (owner == playerNumber && ownerCanPass) {
                continue;
            }

            if (left < bombX + GameModel.TILE_SIZE &&
                left + collisionSize > bombX &&
                top < bombY + GameModel.TILE_SIZE &&
                top + collisionSize > bombY) {
                return true;
            }
        }

        return false;
    }
}
