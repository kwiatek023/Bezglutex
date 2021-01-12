package layout.controllers;

import database.connection.SessionManager;
import database.connection.exceptions.FailedLoginException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import layout.App;
import layout.ConcreteLayoutFactory;
import layout.LayoutFactory;

import java.io.IOException;

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

    public void logIn() {
        try {
            LayoutFactory layoutFactory = new ConcreteLayoutFactory();
            String layout = layoutFactory.getLayout(sessionManager.openSessionForUser(login.getText(), password.getText()));
            App.setRoot(layout);
        } catch (FailedLoginException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Login failed. Try again.");
            alert.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
