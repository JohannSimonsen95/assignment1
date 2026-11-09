
package bombastic.menu.chatmenu;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ChatMenu extends VBox {

    private final ListView<String> messages = new ListView<>();
    private final ListView<String> users = new ListView<>();
    private final TextArea messageInput = new TextArea();

    private final Button sendButton = new Button("Send");
    private final Button backButton = new Button("Main menu");

    public ChatMenu() {
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

    public ListView<String> getMessages() {
        return messages;
    }

    public TextArea getMessageInput() {
        return messageInput;
    }

    public Button getSendButton() {
        return sendButton;
    }

    public Button getBackButton() {
        return backButton;
    }

    public ListView<String> getUsers() {
        return users;
    }
}
