
package bombastic.game.entities.powerups;

import java.io.File;

import bombastic.game.entities.Entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class PowerUp extends Entity {

    private final Image image;

    public PowerUp(double x, double y, int size, String sprite) {
        super(x, y, size);

        image = new Image(
            new File("Assets/powerups/" + sprite)
                .toURI().toString()
        );
    }

    @Override
    public boolean isOccupying() {
        return false;
    }

    @Override
    public void update(double dt) {
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(image, x, y, size, size);
    }

    @Override
    public abstract void interact(Entity entity);
}
