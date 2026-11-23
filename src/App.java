
import bombastic.Controller;
import bombastic.Model;
import bombastic.menu.mainmenu.MainMenu;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {

        Model model = new Model();
        MainMenu mainMenu = new MainMenu(model);

        new Controller(stage, model, mainMenu);

        Scene scene = new Scene(mainMenu, 800, 600);

        stage.setTitle("Bombastic");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
