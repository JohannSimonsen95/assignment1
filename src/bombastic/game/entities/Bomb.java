
package bombastic.game.entities;

import java.io.File;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Bomb extends Entity {

    private final Image image;
    private final Player owner;
    private final int range;

    private double timer = 3.0;
    private boolean ownerCanPass = true;

    public Bomb(double x, double y, int size, Player owner) {
        super(x, y, size);

        this.owner = owner;
        this.range = owner.getBombRange();

        image = new Image(
            new File("Assets/bombs/bomb_0.png")
                .toURI().toString()
        );
    }

    public Player getOwner() {
        return owner;
    }

    public int getRange() {
        return range;
    }

    public boolean isOwnerCanPass() {
        return ownerCanPass;
    }

    public boolean canPass(Player player) {
        return player == owner && ownerCanPass;
    }

    public void updateOwnerPass() {
        double margin =
            (size - owner.getCollisionSize()) / 2.0;

        double left = owner.getX() + margin;
        double top = owner.getY() + margin;
        double right = left + owner.getCollisionSize();
        double bottom = top + owner.getCollisionSize();

        if (right <= x || left >= x + size ||
            bottom <= y || top >= y + size) {
            ownerCanPass = false;
        }
    }

    public boolean isExploded() {
        return timer <= 0;
    }

    public void explode() {
        timer = 0;
    }

    @Override
    public boolean isOccupying() {
        return true;
    }

    @Override
    public void interact(Entity entity) {
        if (entity instanceof Explosion) {
            explode();
        }
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
