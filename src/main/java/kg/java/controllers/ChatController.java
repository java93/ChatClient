package kg.java.controllers;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import kg.java.messages.Message;

public class ChatController {

    @FXML
    private Label onlineUsersCountLabel;

    @FXML
    private ListView usersListView;

    @FXML
    private ListView messageListView;

    @FXML
    private TextArea messageTextArea;

    @FXML
    private Button sendButton;

    @FXML
    private Label minimizeButtonLabel;

    @FXML
    private Label closeButtonLabel;

    @FXML
    private Label usernameLabel;

    @FXML
    void sendMessage(ActionEvent event) {

    }

    public void setUsernameLabel(String username) {
        usernameLabel.setText(username);
    }

    public synchronized void addToChat(Message message) {
        Task<HBox> messages = new Task<HBox>() {
            @Override
            protected HBox call() throws Exception {
                Label label = new Label(message.getName() + " : " + message.getMsg());
                HBox hBox = new HBox();
                hBox.getChildren().add(label);
                return hBox;
            }
        };

        messages.setOnSucceeded(event->{
            messageListView.getItems().add(messages.getValue());
        });
    }

    public void setUserList(Message message) {
        System.out.println(message.getName() + " connected");
        System.out.println("Online" + message.getUsers());
        // TODO: add userlist management
    }
}
