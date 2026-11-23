
package bombastic;

import bombastic.menu.mainmenu.MainMenu;
import bombastic.menu.settingsmenu.SettingsMenu;
import bombastic.menu.chatmenu.ChatMenu;

import javafx.application.Platform;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Controller {

    private final Stage stage;
    private final MainMenu mainMenu;
    private final SettingsMenu settingsMenu;
    private final ChatMenu chatMenu;
    private final Model model;

    public Controller(Stage stage, Model model, MainMenu mainMenu) {
        this.stage = stage;
        this.model = model;
        this.mainMenu = mainMenu;
        this.settingsMenu = new SettingsMenu(model);
        this.chatMenu = new ChatMenu(model);

        setupActions();
    }

    private void setupActions() {

        mainMenu.setOnStart(() -> {
            stage.getScene().setRoot(new StackPane());
        });

        mainMenu.setOnChat(() -> {
            stage.getScene().setRoot(chatMenu);
        });

        mainMenu.setOnSettings(() -> {
            stage.getScene().setRoot(settingsMenu);
        });

        mainMenu.setOnExit(() -> {
            Platform.exit();
        });

        settingsMenu.setOnBack(() -> {
            stage.getScene().setRoot(mainMenu);
        });

        chatMenu.setOnBack(() -> {
            stage.getScene().setRoot(mainMenu);
        });

        chatMenu.setOnSend(message -> {
            model.sendMessage(message);
        });
    }
}
