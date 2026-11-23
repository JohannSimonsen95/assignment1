
package bombastic;

import bombastic.game.GameModel;
import bombastic.game.GameView;
import bombastic.game.GameController;

import bombastic.menu.mainmenu.MainMenu;
import bombastic.menu.settingsmenu.SettingsMenu;
import bombastic.menu.chatmenu.ChatMenu;

import javafx.application.Platform;
import javafx.stage.Stage;

public class Controller {

    private final Stage stage;
    private final MainMenu mainMenu;
    private final SettingsMenu settingsMenu;
    private final ChatMenu chatMenu;
    private final Model model;

    private GameController gameController;

    public Controller(Stage stage, Model model, MainMenu mainMenu) {
        this.stage = stage;
        this.model = model;
        this.mainMenu = mainMenu;

        settingsMenu = new SettingsMenu(model);
        chatMenu = new ChatMenu(model);

        setupActions();
    }

    private void setupActions() {
        mainMenu.setOnStart(() -> startGame());

        mainMenu.setOnChat(() ->
            stage.getScene().setRoot(chatMenu)
        );

        mainMenu.setOnSettings(() ->
            stage.getScene().setRoot(settingsMenu)
        );

        mainMenu.setOnExit(() -> Platform.exit());

        settingsMenu.setOnBack(() ->
            stage.getScene().setRoot(mainMenu)
        );

        chatMenu.setOnBack(() ->
            stage.getScene().setRoot(mainMenu)
        );

        chatMenu.setOnSend(message ->
            model.sendMessage(message)
        );
    }

    private void startGame() {
        if (gameController != null) {
            gameController.stop();
        }

        GameModel gameModel = new GameModel();
        GameView gameView = new GameView(gameModel);

        gameController = new GameController(
            gameModel, gameView
        );

        stage.getScene().setRoot(gameView);
        gameController.start();
    }
}
