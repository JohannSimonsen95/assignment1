
package bombastic.menu.mainmenu;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class MainMenu extends VBox {

    private final Button startButton = new Button("Start game");
    private final Button chatButton = new Button("Chat");
    private final Button settingsButton = new Button("Settings");
    private final Button exitButton = new Button("Exit");

    public MainMenu() {
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

    public Button getStartButton() {
        return startButton;
    }

    public Button getChatButton() {
        return chatButton;
    }

    public Button getSettingsButton() {
        return settingsButton;
    }

    public Button getExitButton() {
        return exitButton;
    }
}
