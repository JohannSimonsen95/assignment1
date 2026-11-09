
package bombastic;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.Background;

public class Model {

    private final ObjectProperty<String> background =
            new SimpleObjectProperty<>("bg1.png");

    private final ObjectProperty<Background> activeBackground =
            new SimpleObjectProperty<>();

    private final ObjectProperty<String> arena =
            new SimpleObjectProperty<>("Arena 1");

    private final ObservableList<String> chatMessages =
            FXCollections.observableArrayList();
    private final ObservableList<String> onlineUsers =
            FXCollections.observableArrayList("Bent", "Karen", "Sammy");
            
    public String getBackground() {
        return background.get();
    }
    public ObservableList<String> getOnlineUsers() {
        return onlineUsers;
    }
    public void setBackground(String background) {
        this.background.set(background);
    }

    public ObjectProperty<Background> activeBackgroundProperty() {
        return activeBackground;
    }

    public void setActiveBackground(Background background) {
        activeBackground.set(background);
    }

    public String getArena() {
        return arena.get();
    }

    public void setArena(String arena) {
        this.arena.set(arena);
    }

    public ObservableList<String> getChatMessages() {
        return chatMessages;
    }
}
