
package bombastic;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Model {

    private final ObjectProperty<String> background =
            new SimpleObjectProperty<>("bg1.png");

    private final ObjectProperty<String> arena =
            new SimpleObjectProperty<>("Arena 1");

    private final ObservableList<String> chatMessages =
            FXCollections.observableArrayList();

    // Background
    public ObjectProperty<String> backgroundProperty() {
        return background;
    }

    public String getBackground() {
        return background.get();
    }

    public void setBackground(String background) {
        this.background.set(background);
    }

    // Arena
    public String getArena() {
        return arena.get();
    }

    public void setArena(String arena) {
        this.arena.set(arena);
    }

    // Chat messages
    public ObservableList<String> getChatMessages() {
        return chatMessages;
    }
}
