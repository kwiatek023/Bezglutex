package layout.controllers;

import database.connection.SessionManager;
import database.connection.exceptions.FailedLoginException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    private SessionManager sessionManager;
    @FXML
    public TextField login;

    @FXML
    public PasswordField password;

    @FXML
    public void initialize() {
        sessionManager = SessionManager.getInstance();
    }

    public void logIn(ActionEvent actionEvent) {
        try {
            sessionManager.openSessionForUser(login.getText(), password.getText());
        } catch (FailedLoginException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Login failed. Try again.");
            alert.showAndWait();
        }
    }
}
