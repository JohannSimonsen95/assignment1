
package bombastic.menu.chatmenu;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ChatMenu extends VBox {

    private final ListView<String> messages = new ListView<>();
    private final ListView<String> users = new ListView<>();

    private final TextField messageInput = new TextField();

    private final Button sendButton = new Button("Send");
    private final Button backButton = new Button("Main menu");

    public ChatMenu() {

        setAlignment(Pos.CENTER);
        setSpacing(15);

        Label title = new Label("Chat");

        // Example online users
        users.getItems().addAll(
            "Player1",
            "Player2",
            "Player3"
        );

        // Message input
        messageInput.setPromptText("Write a message...");
        messageInput.setPrefWidth(300);

        // Chat messages and online users
        messages.setPrefSize(400, 300);
        users.setPrefSize(150, 300);

        HBox chatArea = new HBox(10);
        chatArea.setAlignment(Pos.CENTER);
        chatArea.getChildren().addAll(messages, users);

        HBox inputArea = new HBox(10);
        inputArea.setAlignment(Pos.CENTER);
        inputArea.getChildren().addAll(messageInput, sendButton);

        getChildren().addAll(
            title,
            chatArea,
            inputArea,
            backButton
        );
    }

    public ListView<String> getMessages() {
        return messages;
    }

    public TextField getMessageInput() {
        return messageInput;
    }

    public Button getSendButton() {
        return sendButton;
    }

    public Button getBackButton() {
        return backButton;
    }
}
