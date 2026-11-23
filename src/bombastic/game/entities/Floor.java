
package bombastic.game.entities;

import java.io.File;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Floor extends Entity {

    private static final Image image = new Image(
        new File("Assets/terrain/grass.png").toURI().toString()
    );

    public Floor(double x, double y, int size) {
        super(x, y, size);
    }

    @Override
    public boolean isOccupying() {
        return false;
    }

    @Override
    public void interact(Entity entity) {
    }

    @Override
    public void update(double dt) {
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(image, x, y, size, size);
    }
}
