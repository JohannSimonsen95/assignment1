
package bombastic.game;

import bombastic.game.entities.Player;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class GameController {

    private final GameModel model;
    private final GameView view;
    private final AnimationTimer gameLoop;

    private Runnable restartAction;

    public GameController(GameModel model, GameView view) {
        this.model = model;
        this.view = view;

        view.setGameKeyPressed(event -> handleKeyPressed(event));
        view.setGameKeyReleased(event -> handleKeyReleased(event));

        gameLoop = new AnimationTimer() {
            private long lastTime = 0;

            @Override
            public void handle(long now) {
                if (lastTime == 0) {
                    lastTime = now;
                    return;
                }

                double dt = (now - lastTime) / 1_000_000_000.0;
                lastTime = now;

                model.update(dt);
                view.render();
            }
        };
    }

    public void setRestartAction(Runnable action) {
        restartAction = action;
    }

    private void handleKeyPressed(KeyEvent event) {
        KeyCode key = event.getCode();

        if (model.isGameOver()) {
            if (key == KeyCode.R && restartAction != null) {
                restartAction.run();
            }
            return;
        }

        Player player1 = model.getPlayer1();
        Player player2 = model.getPlayer2();

        if (key == KeyCode.W) {
            player1.setUp(true);
        } else if (key == KeyCode.S) {
            player1.setDown(true);
        } else if (key == KeyCode.A) {
            player1.setLeft(true);
        } else if (key == KeyCode.D) {
            player1.setRight(true);
        } else if (key == KeyCode.SPACE) {
            model.placeBomb(player1);
        }

        if (key == KeyCode.I) {
            player2.setUp(true);
        } else if (key == KeyCode.K) {
            player2.setDown(true);
        } else if (key == KeyCode.J) {
            player2.setLeft(true);
        } else if (key == KeyCode.L) {
            player2.setRight(true);
        } else if (key == KeyCode.ENTER) {
            model.placeBomb(player2);
        }
    }

    private void handleKeyReleased(KeyEvent event) {
        Player player1 = model.getPlayer1();
        Player player2 = model.getPlayer2();

        KeyCode key = event.getCode();

        if (key == KeyCode.W) {
            player1.setUp(false);
        } else if (key == KeyCode.S) {
            player1.setDown(false);
        } else if (key == KeyCode.A) {
            player1.setLeft(false);
        } else if (key == KeyCode.D) {
            player1.setRight(false);
        }

        if (key == KeyCode.I) {
            player2.setUp(false);
        } else if (key == KeyCode.K) {
            player2.setDown(false);
        } else if (key == KeyCode.J) {
            player2.setLeft(false);
        } else if (key == KeyCode.L) {
            player2.setRight(false);
        }
    }

    public void start() {
        gameLoop.start();
        Platform.runLater(() -> view.requestGameFocus());
    }

    public void stop() {
        gameLoop.stop();
        model.shutdown();
    }
}
