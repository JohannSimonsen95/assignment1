
package bombastic.menu.chatmenu;

import bombastic.Model;

import java.util.function.Consumer;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ChatMenu extends VBox {

    private final ListView<String> messages = new ListView<>();
    private final ListView<String> users = new ListView<>();
    private final TextArea messageInput = new TextArea();

    private final Button sendButton = new Button("Send");
    private final Button backButton = new Button("Main menu");

    public ChatMenu(Model model) {
        backgroundProperty().bind(model.activeBackgroundProperty());

        messages.setItems(model.getChatMessages());
        users.setItems(model.getOnlineUsers());

        setAlignment(Pos.CENTER);
        setSpacing(10);

        Label usersLabel = new Label("Online users");
        Label messagesLabel = new Label("Messages");

        users.setPrefSize(250, 400);
        messages.setPrefSize(300, 400);

        usersLabel.setStyle("-fx-text-fill: white; -fx-font-size: 20px;");
        messagesLabel.setStyle("-fx-text-fill: white; -fx-font-size: 20px;");

        VBox usersArea = new VBox(5, usersLabel, users);
        VBox messagesArea = new VBox(5, messagesLabel, messages);

        HBox chatArea = new HBox(15, usersArea, messagesArea);
        chatArea.setAlignment(Pos.CENTER);

        messageInput.setPromptText("Enter message here...");
        messageInput.setPrefSize(565, 120);
        messageInput.setWrapText(true);

        VBox buttons = new VBox(10, sendButton, backButton);
        buttons.setAlignment(Pos.TOP_CENTER);

        HBox inputArea = new HBox(10, messageInput, buttons);
        inputArea.setAlignment(Pos.TOP_CENTER);

        getChildren().addAll(chatArea, inputArea);
    }

    public void setOnSend(Consumer<String> action) {
        sendButton.setOnAction(event -> {
            String message = messageInput.getText();

            if (!message.isBlank()) {
                action.accept(message);
                messageInput.clear();
            }
        });

        messageInput.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                sendButton.fire();
                event.consume();
            }
        });
    }

    public void setOnBack(Runnable action) {
        backButton.setOnAction(event -> action.run());
    }
}
