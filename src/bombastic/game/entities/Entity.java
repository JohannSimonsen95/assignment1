package bombastic.game.entities;

import javafx.scene.canvas.GraphicsContext;

public abstract class Entity {

    protected double x;
    protected double y;
    protected int size;

    public Entity(double x, double y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getSize() {
        return size;
    }

    public abstract boolean isOccupying();

    public abstract void interact(Entity entity);

    public abstract void update(double dt);

    public abstract void render(GraphicsContext gc);
}