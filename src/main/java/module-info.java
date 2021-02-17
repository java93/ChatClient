module ChatClient {
    requires javafx.controls;
    requires javafx.fxml;

    opens kg.java to javafx.fxml;
    opens kg.java.controllers to javafx.fxml;
    opens kg.java.messages to javafx.fxml;
    opens kg.java.services to javafx.fxml;

    exports kg.java;
}