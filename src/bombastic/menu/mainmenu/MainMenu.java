
package bombastic.menu.mainmenu;

import bombastic.Model;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class MainMenu extends VBox {

    private final Button startButton = new Button("Start game");
    private final Button chatButton = new Button("Chat");
    private final Button settingsButton = new Button("Settings");
    private final Button exitButton = new Button("Exit");

    public MainMenu(Model model) {
        backgroundProperty().bind(model.activeBackgroundProperty());

        setAlignment(Pos.CENTER);
        setSpacing(15);

        startButton.setPrefWidth(200);
        chatButton.setPrefWidth(200);
        settingsButton.setPrefWidth(200);
        exitButton.setPrefWidth(200);

        getChildren().addAll(
            startButton,
            chatButton,
            settingsButton,
            exitButton
        );
    }

    public void setOnStart(Runnable action) {
        startButton.setOnAction(event -> action.run());
    }

    public void setOnChat(Runnable action) {
        chatButton.setOnAction(event -> action.run());
    }

    public void setOnSettings(Runnable action) {
        settingsButton.setOnAction(event -> action.run());
    }

    public void setOnExit(Runnable action) {
        exitButton.setOnAction(event -> action.run());
    }
}
