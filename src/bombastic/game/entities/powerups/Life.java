
package bombastic.game.entities.powerups;

import bombastic.game.entities.Entity;
import bombastic.game.entities.Player;

public class Life extends PowerUp {

    public Life(double x, double y, int size) {
        super(x, y, size, "life.png");
    }

    @Override
    public void interact(Entity entity) {
        if (entity instanceof Player) {
            Player player = (Player) entity;
            player.increaseLives();
        }
    }
}
