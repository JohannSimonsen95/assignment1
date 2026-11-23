
package bombastic.game.entities;

import java.io.File;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Player extends Entity {

    private final Image image;

    private int lives = 3;
    private int maxBombs = 1;
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

    @Override
    public boolean isOccupying() {
        return true;
    }

    @Override
    public void interact(Entity entity) {
    }

    @Override
    public void update(double dt) {
        double dx = 0;
        double dy = 0;

        if (up) {
            dy -= 1;
        }

        if (down) {
            dy += 1;
        }

        if (left) {
            dx -= 1;
        }

        if (right) {
            dx += 1;
        }

        if (dx != 0 && dy != 0) {
            double diagonal = Math.sqrt(2);
            dx /= diagonal;
            dy /= diagonal;
        }

        x += dx * speed * dt;
        y += dy * speed * dt;
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(image, x, y, size, size);
    }
}
