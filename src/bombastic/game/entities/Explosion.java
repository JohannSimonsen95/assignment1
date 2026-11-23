
package bombastic.game.entities;

import java.io.File;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Explosion extends Entity {

    private final Image image;
    private final int groupId;
    private double timer = 0.5;

    public Explosion(double x, double y, int size, int groupId) {
        super(x, y, size);

        this.groupId = groupId;

        image = new Image(
            new File("Assets/explosions/explosion_3.png")
                .toURI().toString()
        );
    }

    public int getGroupId() {
        return groupId;
    }

    public boolean isFinished() {
        return timer <= 0;
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
        timer -= dt;
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(image, x, y, size, size);
    }
}
