
package bombastic.game.entities;

import java.io.File;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class DestructibleWall extends Entity {

    private final Image image;
    private boolean destroyed = false;

    public DestructibleWall(double x, double y, int size) {
        super(x, y, size);

        image = new Image(
            new File("Assets/terrain/crate.png")
                .toURI().toString()
        );
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    @Override
    public boolean isOccupying() {
        return !destroyed;
    }

    @Override
    public void interact(Entity entity) {
        if (entity instanceof Explosion) {
            destroyed = true;
        }
    }

    @Override
    public void update(double dt) {
    }

    @Override
    public void render(GraphicsContext gc) {
        if (!destroyed) {
            gc.drawImage(image, x, y, size, size);
        }
    }
}
