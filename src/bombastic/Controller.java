
package bombastic;

import bombastic.menu.mainmenu.MainMenu;
import bombastic.menu.settingsmenu.SettingsMenu;
import bombastic.menu.chatmenu.ChatMenu;

import java.io.File;

import javafx.application.Platform;
import javafx.scene.control.MenuItem;
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

        mainMenu.backgroundProperty().bind(model.activeBackgroundProperty());
        settingsMenu.backgroundProperty().bind(model.activeBackgroundProperty());
        chatMenu.backgroundProperty().bind(model.activeBackgroundProperty());

        setupButtons();
        updateBackground();
    }

    private void setupButtons() {

        mainMenu.getStartButton().setOnAction(event -> {
            stage.getScene().setRoot(new StackPane());
        });

        mainMenu.getChatButton().setOnAction(event -> {
            stage.getScene().setRoot(chatMenu);
        });

        mainMenu.getSettingsButton().setOnAction(event -> {
            stage.getScene().setRoot(settingsMenu);
        });

        settingsMenu.getBackButton().setOnAction(event -> {
            stage.getScene().setRoot(mainMenu);
        });

        chatMenu.getBackButton().setOnAction(event -> {
            stage.getScene().setRoot(mainMenu);
        });

        for (MenuItem item : settingsMenu.getBackgroundMenu().getItems()) {
            item.setOnAction(event -> {
                String selected = item.getText();
                settingsMenu.getBackgroundMenu().setText(selected);
                model.setBackground(selected);
                updateBackground();
            });
        }

        for (MenuItem item : settingsMenu.getArenaMenu().getItems()) {
            item.setOnAction(event -> {
                String selected = item.getText();
                settingsMenu.getArenaMenu().setText(selected);
                model.setArena(selected);
            });
        }

        chatMenu.getMessages().setItems(model.getChatMessages());
        chatMenu.getUsers().setItems(model.getOnlineUsers());

        chatMenu.getSendButton().setOnAction(event -> {
            String message = chatMenu.getMessageInput().getText();

            if (!message.isBlank()) {
                model.getChatMessages().add(message);
                chatMenu.getMessageInput().clear();
            }
        });

        chatMenu.getMessageInput().setOnKeyPressed(event -> {
            if (event.getCode() == javafx.scene.input.KeyCode.ENTER) {
                chatMenu.getSendButton().fire();
                event.consume();
            }
        });

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

        model.setActiveBackground(background);
    }
}
