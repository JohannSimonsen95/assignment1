
package bombastic;

import bombastic.menu.mainmenu.MainMenu;
import bombastic.menu.settingsmenu.SettingsMenu;
import bombastic.menu.chatmenu.ChatMenu;

import java.io.File;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Controller {

    private final Stage stage;
    private final MainMenu mainMenu;
    private final SettingsMenu settingsMenu;
    private final ChatMenu chatMenu;
    private final Model model;

    public Controller(Stage stage, MainMenu mainMenu) {
        this.stage = stage;
        this.mainMenu = mainMenu;
        this.settingsMenu = new SettingsMenu();
        this.chatMenu = new ChatMenu();
        this.model = new Model();

        setupButtons();
        updateBackground();
    }

    private void setupButtons() {

        // Start game
        mainMenu.getStartButton().setOnAction(event -> {
            stage.getScene().setRoot(new StackPane());
        });

        // Open Chat menu
        mainMenu.getChatButton().setOnAction(event -> {
            stage.getScene().setRoot(chatMenu);
        });

        // Open Settings menu
        mainMenu.getSettingsButton().setOnAction(event -> {
            stage.getScene().setRoot(settingsMenu);
        });

        // Return from Settings to Main menu
        settingsMenu.getBackButton().setOnAction(event -> {
            stage.getScene().setRoot(mainMenu);
        });

        // Return from Chat to Main menu
        chatMenu.getBackButton().setOnAction(event -> {
            stage.getScene().setRoot(mainMenu);
        });

        // Change background
        settingsMenu.getBackgroundBox().setOnAction(event -> {
            model.setBackground(
                settingsMenu.getBackgroundBox().getValue()
            );

            updateBackground();
        });

        // Change arena
        settingsMenu.getArenaBox().setOnAction(event -> {
            model.setArena(
                settingsMenu.getArenaBox().getValue()
            );

            System.out.println("Arena: " + model.getArena());
        });

        // Exit application
        mainMenu.getExitButton().setOnAction(event -> {
            Platform.exit();
        });
    }

    private void updateBackground() {

        String path = new File(
            "Assets/" + model.getBackground()
        ).toURI().toString();

        Image image = new Image(path);

        BackgroundImage backgroundImage = new BackgroundImage(
            image,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            new BackgroundSize(
                100, 100,
                true, true,
                false, true
            )
        );

        Background background = new Background(backgroundImage);

        mainMenu.setBackground(background);
        settingsMenu.setBackground(background);
        chatMenu.setBackground(background);
    }
}
