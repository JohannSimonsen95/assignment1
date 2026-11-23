
package bombastic.game;

import java.io.File;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class GameView extends StackPane {

    private final int TILE_SIZE = 40;
    private final int COLUMNS = 15;
    private final int ROWS = 11;

    private final Canvas canvas = new Canvas(800, 600);

    private final Image grass = new Image(
        new File("Assets/terrain/grass.png").toURI().toString()
    );

    private final Image wallImage = new Image(
        new File("Assets/terrain/wall.png").toURI().toString()
    );

    private final Image crateImage = new Image(
        new File("Assets/terrain/crate.png").toURI().toString()
    );

    public GameView() {
        getChildren().add(canvas);
        canvas.setFocusTraversable(true);

        drawArena();
    }

    private void drawArena() {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        double offsetX = (canvas.getWidth() - COLUMNS * TILE_SIZE) / 2;
        double offsetY = (canvas.getHeight() - ROWS * TILE_SIZE) / 2;

        gc.setFill(Color.DARKGREEN);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {

                double x = offsetX + col * TILE_SIZE;
                double y = offsetY + row * TILE_SIZE;

                boolean wall =
                    row == 0 ||
                    row == ROWS - 1 ||
                    col == 0 ||
                    col == COLUMNS - 1 ||
                    (row % 2 == 0 && col % 2 == 0);

                boolean crate =
                    !wall &&
                    (row + col) % 3 == 0;

                gc.drawImage(grass, x, y, TILE_SIZE, TILE_SIZE);

                if (wall) {
                    gc.drawImage(wallImage, x, y, TILE_SIZE, TILE_SIZE);
                } else if (crate) {
                    gc.drawImage(crateImage, x, y, TILE_SIZE, TILE_SIZE);
                }
            }
        }
    }
}
