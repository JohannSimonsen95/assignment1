
package bombastic.game.entities;

import java.io.File;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class IndestructibleWall extends Entity {

    private static final Image image = new Image(
        new File("Assets/terrain/wall.png").toURI().toString()
    );

    public IndestructibleWall(double x, double y, int size) {
        super(x, y, size);
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
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(image, x, y, size, size);
    }
}
