package layout.controllers;

import connection.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
        if(sessionManager.sessionIsOpen()) {

        }
    }
}
