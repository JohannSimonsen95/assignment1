
package bombastic;

import java.io.File;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

public class Model {

    private final StringProperty background =
            new SimpleStringProperty("bg1.png");

    private final StringProperty arena =
            new SimpleStringProperty("Arena 1");

    private final ObjectProperty<Background> activeBackground =
            new SimpleObjectProperty<>();

    private final ObservableList<String> chatMessages =
            FXCollections.observableArrayList();

    private final ObservableList<String> onlineUsers =
            FXCollections.observableArrayList("Bent", "Karen", "Sammy");

    public Model() {
        background.addListener((observable, oldValue, newValue) -> {
            updateBackground();
        });

        updateBackground();
    }

    public void setBackground(String background) {
        this.background.set(background);
    }

    public StringProperty backgroundProperty() {
        return background;
    }

    public void setArena(String arena) {
        this.arena.set(arena);
    }

    public StringProperty arenaProperty() {
        return arena;
    }

    public ObjectProperty<Background> activeBackgroundProperty() {
        return activeBackground;
    }

    public ObservableList<String> getChatMessages() {
        return chatMessages;
    }

    public ObservableList<String> getOnlineUsers() {
        return onlineUsers;
    }

    public void sendMessage(String message) {
        if (message != null && !message.isBlank()) {
            chatMessages.add(message);
        }
    }

    private void updateBackground() {
        String path = new File(
            "Assets/backgrounds/" + background.get()
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

        activeBackground.set(new Background(backgroundImage));
    }
}
