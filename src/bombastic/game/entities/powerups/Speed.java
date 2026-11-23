
package bombastic.game.entities.powerups;

import bombastic.game.entities.Entity;
import bombastic.game.entities.Player;

public class Speed extends PowerUp {

    public Speed(double x, double y, int size) {
        super(x, y, size, "speed.png");
    }

    @Override
    public void interact(Entity entity) {
        if (entity instanceof Player) {
            Player player = (Player) entity;
            player.increaseSpeed();
        }
    }
}
