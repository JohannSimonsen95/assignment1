
package bombastic.game.entities;

import java.io.File;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Bomb extends Entity {

    private final Image image;
    private final Player owner;

    private double timer = 3.0;

    public Bomb(double x, double y, int size, Player owner) {
        super(x, y, size);

        this.owner = owner;

        image = new Image(
            new File("Assets/bombs/bomb_0.png")
                .toURI().toString()
        );
    }

    public Player getOwner() {
        return owner;
    }

    public boolean isExploded() {
        return timer <= 0;
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
        timer -= dt;
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(image, x, y, size, size);
    }
}
