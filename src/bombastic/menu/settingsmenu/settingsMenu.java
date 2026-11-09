
package bombastic.menu.settingsmenu;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;

public class SettingsMenu extends VBox {

    private final MenuButton backgroundMenu = new MenuButton("bg1.png");
    private final MenuButton arenaMenu = new MenuButton("Arena 1");
    private final Button backButton = new Button("Main menu");

    public SettingsMenu() {
        setAlignment(Pos.CENTER);
        setSpacing(15);

        Label title = new Label("Settings");

        MenuItem bg1 = new MenuItem("bg1.png");
        MenuItem bg2 = new MenuItem("bg2.png");
        MenuItem bg3 = new MenuItem("bg3.png");

        backgroundMenu.getItems().addAll(bg1, bg2, bg3);

        MenuItem arena1 = new MenuItem("Arena 1");
        MenuItem arena2 = new MenuItem("Arena 2");
        MenuItem arena3 = new MenuItem("Arena 3");

        arenaMenu.getItems().addAll(arena1, arena2, arena3);

        backgroundMenu.setPrefWidth(200);
        arenaMenu.setPrefWidth(200);
        backButton.setPrefWidth(200);

        getChildren().addAll(
            title,
            new Label("Background"),
            backgroundMenu,
            new Label("Arena"),
            arenaMenu,
            backButton
        );
    }

    public MenuButton getBackgroundMenu() {
        return backgroundMenu;
    }

    public MenuButton getArenaMenu() {
        return arenaMenu;
    }

    public Button getBackButton() {
        return backButton;
    }
}
