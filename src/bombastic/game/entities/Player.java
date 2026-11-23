
package bombastic.game.entities;

import java.io.File;
import java.util.HashSet;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Player extends Entity {

    private final Image image;
    private final HashSet<Integer> explosionHits = new HashSet<>();

    private int lives = 3;
    private int maxBombs = 1;
    private int bombRange = 2;
    private double speed = 150;

    private final int collisionSize = 30;

    private boolean up;
    private boolean down;
    private boolean left;
    private boolean right;

    public Player(double x, double y, int size, String sprite) {
        super(x, y, size);

        image = new Image(
            new File("Assets/players/" + sprite)
                .toURI().toString()
        );
    }

    public int getCollisionSize() {
        return collisionSize;
    }

    public int getLives() {
        return lives;
    }

    public int getMaxBombs() {
        return maxBombs;
    }

    public int getBombRange() {
        return bombRange;
    }

    public boolean isDead() {
        return lives <= 0;
    }

    public void increaseCapacity() {
        maxBombs++;
    }

    public void increaseRange() {
        bombRange++;
    }

    public void increaseSpeed() {
        speed += 25;
    }

    public void increaseLives() {
        lives++;
    }

    public void takeDamage() {
        if (!isDead()) {
            lives--;
        }
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getMovementX(double dt) {
        if (isDead()) {
            return 0;
        }

        double dx = 0;
        double dy = 0;

        if (left) {
            dx--;
        }
        if (right) {
            dx++;
        }
        if (up) {
            dy--;
        }
        if (down) {
            dy++;
        }

        if (dx != 0 && dy != 0) {
            dx /= Math.sqrt(2);
        }

        return dx * speed * dt;
    }

    public double getMovementY(double dt) {
        if (isDead()) {
            return 0;
        }

        double dx = 0;
        double dy = 0;

        if (left) {
            dx--;
        }
        if (right) {
            dx++;
        }
        if (up) {
            dy--;
        }
        if (down) {
            dy++;
        }

        if (dx != 0 && dy != 0) {
            dy /= Math.sqrt(2);
        }

        return dy * speed * dt;
    }

    @Override
    public boolean isOccupying() {
        return !isDead();
    }

    @Override
    public void interact(Entity entity) {
        if (entity instanceof Explosion) {
            Explosion explosion = (Explosion) entity;

            if (explosionHits.add(explosion.getGroupId())) {
                takeDamage();
            }
        }
    }

    @Override
    public void update(double dt) {
    }

    @Override
    public void render(GraphicsContext gc) {
        if (!isDead()) {
            gc.drawImage(image, x, y, size, size);
        }
    }
}
