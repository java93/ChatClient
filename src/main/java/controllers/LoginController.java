package controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.Listener;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController {

    private static LoginController instance;

    @FXML private TextField usernameTextField;
    @FXML private Label closeButtonLabel;
    @FXML private TextField hostnameTextField;
    @FXML private TextField portTextField;

    private static ChatController controller;
    private Scene scene;


    public LoginController() {
        instance=this;
    }

    public static LoginController getInstance() {return instance;}

    @FXML
    void loginButtonAction(ActionEvent event) throws IOException {
        String hostname = this.hostnameTextField.getText();
        int port = Integer.parseInt(portTextField.getText().trim());
        String username = usernameTextField.getText();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/chatView.fxml"));
        Parent parent = fxmlLoader.load();
        controller = fxmlLoader.getController();
        Listener listener = new Listener(hostname, port, username, controller);
        Thread th = new Thread(listener);
        th.start();
        this.scene = new Scene(parent);
    }

    public void showScene(){
        Platform.runLater(() ->{
            Stage stage = (Stage) usernameTextField.getScene().getWindow();

            stage.setScene(this.scene);
            stage.centerOnScreen();
            controller.setUsernameLabel(usernameTextField.getText());
        });
    }

    public void showErrorDialog(String message) {
        Platform.runLater(()->{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setHeaderText(message);
            alert.setContentText("Please check firewall issues and check if the server is running.");
            alert.showAndWait();
        });
    }
}
