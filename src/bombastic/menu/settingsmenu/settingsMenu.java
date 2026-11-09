
package bombastic.menu.settingsmenu;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class SettingsMenu extends VBox {

    private final ComboBox<String> backgroundBox = new ComboBox<>();
    private final ComboBox<String> arenaBox = new ComboBox<>();
    private final Button backButton = new Button("Main menu");

    public SettingsMenu() {

        setAlignment(Pos.CENTER);
        setSpacing(15);

        Label title = new Label("Settings");

        // Background options
        backgroundBox.getItems().addAll(
            "bg1.png",
            "bg2.png",
            "bg3.png"
        );
        backgroundBox.setValue("bg1.png");

        // Arena options
        arenaBox.getItems().addAll(
            "Arena 1",
            "Arena 2",
            "Arena 3"
        );
        arenaBox.setValue("Arena 1");

        // Button and dropdown widths
        backgroundBox.setPrefWidth(200);
        arenaBox.setPrefWidth(200);
        backButton.setPrefWidth(200);

        // Add controls to the layout
        getChildren().addAll(
            title,
            new Label("Background"),
            backgroundBox,
            new Label("Arena"),
            arenaBox,
            backButton
        );
    }

    public ComboBox<String> getBackgroundBox() {
        return backgroundBox;
    }

    public ComboBox<String> getArenaBox() {
        return arenaBox;
    }

    public Button getBackButton() {
        return backButton;
    }
}
