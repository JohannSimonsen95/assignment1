
package bombastic.game;

import java.io.File;

import bombastic.game.entities.Bomb;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class GameView extends StackPane {

    private final GameModel model;

    private final Canvas canvas =
        new Canvas(GameModel.WIDTH, GameModel.HEIGHT);

    private final Image grass = new Image(
        new File("Assets/terrain/grass.png").toURI().toString()
    );

    private final Image wallImage = new Image(
        new File("Assets/terrain/wall.png").toURI().toString()
    );

    private final Image crateImage = new Image(
        new File("Assets/terrain/crate.png").toURI().toString()
    );

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

        int tileSize = GameModel.TILE_SIZE;
        int columns = GameModel.COLUMNS;
        int rows = GameModel.ROWS;

        double offsetX =
            (canvas.getWidth() - columns * tileSize) / 2;

        double offsetY =
            (canvas.getHeight() - rows * tileSize) / 2;

        gc.setFill(Color.DARKGREEN);
        gc.fillRect(
            0, 0,
            canvas.getWidth(),
            canvas.getHeight()
        );

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {

                double x = offsetX + col * tileSize;
                double y = offsetY + row * tileSize;

                gc.drawImage(
                    grass, x, y, tileSize, tileSize
                );

                if (model.getTile(row, col) == 1) {
                    gc.drawImage(
                        wallImage, x, y, tileSize, tileSize
                    );
                } else if (model.getTile(row, col) == 2) {
                    gc.drawImage(
                        crateImage, x, y, tileSize, tileSize
                    );
                }
            }
        }

        for (Bomb bomb : model.getBombs()) {
            bomb.render(gc);
        }

        model.getPlayer1().render(gc);
        model.getPlayer2().render(gc);
    }
}
