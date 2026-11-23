
package bombastic.game;

import bombastic.game.entities.Bomb;
import bombastic.game.entities.DestructibleWall;
import bombastic.game.entities.Entity;
import bombastic.game.entities.Explosion;
import bombastic.game.entities.powerups.PowerUp;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class GameView extends StackPane {

    private final GameModel model;

    private final Canvas canvas =
        new Canvas(GameModel.WIDTH, GameModel.HEIGHT);

    public GameView(GameModel model) {
        this.model = model;

        getChildren().add(canvas);
        canvas.setFocusTraversable(true);

        render();
    }

    public void setGameKeyPressed(EventHandler<KeyEvent> action) {
        canvas.setOnKeyPressed(action);
    }

    public void setGameKeyReleased(EventHandler<KeyEvent> action) {
        canvas.setOnKeyReleased(action);
    }

    public void requestGameFocus() {
        canvas.requestFocus();
    }

    public void render() {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setFill(Color.DARKGREEN);
        gc.fillRect(
            0, 0,
            canvas.getWidth(),
            canvas.getHeight()
        );

        for (Entity floor : model.getFloors()) {
            floor.render(gc);
        }

        for (Entity wall : model.getIndestructibleWalls()) {
            wall.render(gc);
        }

        for (DestructibleWall wall :
                model.getDestructibleWalls()) {
            wall.render(gc);
        }

        for (PowerUp powerUp : model.getPowerUps()) {
            powerUp.render(gc);
        }

        for (Bomb bomb : model.getBombs()) {
            bomb.render(gc);
        }

        for (Explosion explosion : model.getExplosions()) {
            explosion.render(gc);
        }

        model.getPlayer1().render(gc);
        model.getPlayer2().render(gc);

        gc.setFill(Color.WHITE);

        gc.fillText(
            "Player 1 Lives: " + model.getPlayer1().getLives(),
            20, 30
        );

        gc.fillText(
            "Player 2 Lives: " + model.getPlayer2().getLives(),
            650, 30
        );

        if (model.isGameOver()) {
            gc.setFill(Color.YELLOW);
            gc.fillText(model.getWinner(), 350, 50);

            gc.setFill(Color.WHITE);
            gc.fillText("Press R to restart", 350, 70);
        }
    }
}
