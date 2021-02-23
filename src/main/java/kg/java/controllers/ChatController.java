package kg.java.controllers;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import kg.java.messages.Message;
import kg.java.messages.MessageType;
import kg.java.services.Listener;

import java.util.List;

public class ChatController {

    @FXML
    private Label onlineUsersCountLabel;

    @FXML
    private ListView<Label> usersListView;

    @FXML
    private ListView<HBox> messageListView;

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
        Message message = new Message();
        message.setName(usernameLabel.getText());
        message.setType(MessageType.USER);
        message.setMsg(messageTextArea.getText());
        messageTextArea.setText("");
        Listener.sendMessage(message);
    }

    @FXML
    void exitProgram(MouseEvent event) {
        Message message = new Message();
        message.setName(usernameLabel.getText());
        message.setType(MessageType.DISCONNECTED);
        Listener.sendMessage(message);

        Platform.exit();
        System.exit(0);
    }

    public void setUsernameLabel(String username) {
        usernameLabel.setText(username);
    }

    public synchronized void addToChat(Message message) {
        /*Task<HBox> messages = new Task<HBox>() {
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
        });*/
        HBox hBox = new HBox();
        if (message.getName().equals(usernameLabel.getText())) {
            hBox.setAlignment(Pos.CENTER_RIGHT);
        }
        Label msg = new Label(message.getName() + " : " + message.getMsg());
        hBox.getChildren().add(msg);

        messageListView.getItems().add(hBox);

        setUserList(message);
    }

    public void setUserList(Message message) {
        // TODO: add userlist management
        System.out.println("UserList updating with users: " + message.getUsers());
        Platform.runLater(() ->{
            onlineUsersCountLabel.setText(String.valueOf(message.getUsers().size()));
            ObservableList<Label> items = usersListView.getItems();
            items.clear();
            message.getUsers().stream().map(Label::new).forEach(items::add);
        });


    }
}
