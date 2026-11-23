
package bombastic.game.entities.powerups;

import bombastic.game.entities.Entity;
import bombastic.game.entities.Player;

public class Range extends PowerUp {

    public Range(double x, double y, int size) {
        super(x, y, size, "range.png");
    }

    @Override
    public void interact(Entity entity) {
        if (entity instanceof Player) {
            Player player = (Player) entity;
            player.increaseRange();
        }
    }
}
