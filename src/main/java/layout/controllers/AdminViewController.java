package layout.controllers;

import database.UserType;
import database.connection.SessionManager;
import database.entities.UsersEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import layout.App;
import layout.exceptions.CannotRemoveSessionOwnerException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AdminViewController {
    private SessionManager sessionManager;
    private final ObservableList<UsersEntity> usersEntities = FXCollections.observableArrayList();

    @FXML
    public TextField loginToAdd;

    @FXML
    public PasswordField passwordToAdd;

    @FXML
    public ChoiceBox<UserType> userTypeChoiceBox;

    @FXML
    public TextField loginToResetPassword;

    @FXML
    public TextField loginToRemove;

    @FXML
    public TableView<UsersEntity> tableView;

    @FXML
    public TableColumn<UsersEntity, String> loginColumn;

    @FXML
    public TableColumn<UsersEntity, String> typeColumn;

    public void initialize() {
        sessionManager = SessionManager.getInstance();
        userTypeChoiceBox.getItems().addAll(UserType.values());

        Session session = sessionManager.openSession();
        Query<UsersEntity> query = session.createQuery("FROM UsersEntity", UsersEntity.class);
        usersEntities.addAll(query.getResultList());
        sessionManager.closeSession();

        tableView.setItems(usersEntities);

        loginColumn.setCellValueFactory(new PropertyValueFactory<>("login"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
    }

    public void addUserClicked(ActionEvent actionEvent) {
        if (!loginToAdd.getText().isBlank() && !passwordToAdd.getText().isBlank() && userTypeChoiceBox.getValue() != null) {

            try {
                addUser(loginToAdd.getText(), passwordToAdd.getText(), userTypeChoiceBox.getValue());
                displayInfoAlert("User with login \"" + loginToAdd.getText() + "\" successfully added.");
            } catch (Exception ex) {
                displayErrorAlert("User with login \"" + loginToAdd.getText() + "\" exists.");
            }
        } else {
            displayErrorAlert("You should enter login, password and choose user type in order to add new user.");
        }

        loginToAdd.setText("");
        passwordToAdd.setText("");
        userTypeChoiceBox.setValue(null);
    }


    private void addUser(String login, String password, UserType type) throws Exception {
        UsersEntity newUser = new UsersEntity();
        newUser.setLogin(login);
        newUser.setPassword(password);
        newUser.setType(type);

        Session session = sessionManager.openSession();
        session.beginTransaction();
        session.save(newUser);
        session.getTransaction().commit();
        sessionManager.closeSession();

        usersEntities.add(newUser);
    }

    public void resetPasswordClicked(ActionEvent actionEvent) {
        if (!loginToResetPassword.getText().isBlank()) {
            String login = loginToResetPassword.getText();
            String message = "Do you confirm you want to reset password for user with login \"" + login + "\"?";

            if (confirm(message)) {
                try {
                    resetPassword(loginToResetPassword.getText());
                    displayInfoAlert("Password of user with login \"" + login + "\" successfully reset.");
                } catch (Exception ex) {
                    displayErrorAlert("User with login \"" + login + "\" doesn't exists.");
                }
            }
        } else {
            displayErrorAlert("You should enter user's login in order to reset user's password.");
        }

        loginToResetPassword.setText("");
    }

    private void resetPassword(String login) throws Exception {
        Session session = sessionManager.openSession();
        session.beginTransaction();

        StoredProcedureQuery procedureQuery = session.createStoredProcedureQuery("reset_password").
                registerStoredProcedureParameter("login", String.class, ParameterMode.IN)
                .setParameter("login", login);
        procedureQuery.execute();

        session.getTransaction().commit();
        sessionManager.closeSession();
    }

    public void removeUserClicked(ActionEvent actionEvent) {
        if (!loginToRemove.getText().isBlank()) {
            String login = loginToRemove.getText();
            String message = "Do you confirm you want to remove user with login \"" + login + "\"?";

            if (confirm(message)) {
                try {
                    removeUser(login);
                    displayInfoAlert("User with login \"" + login + "\" successfully removed.");
                } catch (CannotRemoveSessionOwnerException sessionOwnerEx) {
                    displayErrorAlert("You can't remove yourself!");
                } catch (Exception ex) {
                    displayErrorAlert("User with login \"" + login + "\" doesn't exists.");
                }
            }
        } else {
            displayErrorAlert("You should enter user's login in order to remove user.");
        }

        loginToRemove.setText("");
    }

    private void removeUser(String login) throws CannotRemoveSessionOwnerException {
        if (login.equals(sessionManager.getSessionsOwner())) {
            throw new CannotRemoveSessionOwnerException();
        }

        UsersEntity userToRemove = null;

        for (UsersEntity user : usersEntities) {
            if (user.getLogin().equals(login)) {
                userToRemove = user;
                break;
            }
        }

        Session session = sessionManager.openSession();
        session.beginTransaction();
        session.remove(userToRemove);
        session.getTransaction().commit();
        sessionManager.closeSession();

        usersEntities.remove(userToRemove);
    }

    public void makeBackup() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose directory to save backup");
        File file = directoryChooser.showDialog(App.getStage());

        if (file != null) {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss");
            String currentDate = formatter.format(calendar.getTime());

            String filePath = file.getAbsolutePath() + "/bezglutex-backup-" + currentDate;

            String executedCmd = "mysqldump -u admin -padmin --databases bezglutex";

            try {
                Process runtimeProcess = Runtime.getRuntime().exec(executedCmd);
                InputStream inputStream = new BufferedInputStream(runtimeProcess.getInputStream());
                OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(filePath));

                int counter;
                byte[] bytes = new byte[1024];
                while ((counter = inputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, counter);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            displayInfoAlert("Backup successfully made");
        } else {
            displayErrorAlert("You have to select directory to save backup!");
        }
    }

    public void restoreFromBackup() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select backup to restore");
        File file = fileChooser.showOpenDialog(App.getStage());

        if (file != null) {
            String executedCmd = "mysql --user=admin --password=admin bezglutex -e source " + file.getAbsolutePath();

            try {
                Runtime.getRuntime().exec(executedCmd);
            } catch (IOException e) {
                e.printStackTrace();
            }

            displayInfoAlert("Database successfully restored");
        } else {
            displayErrorAlert("You have to select backup to restore database!");
        }
    }

    private void displayErrorAlert(String contextText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText(contextText);
        alert.showAndWait();
    }

    private void displayInfoAlert(String contextText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText(contextText);
        alert.showAndWait();
    }

    private boolean confirm(String contextText) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setContentText(contextText);

        alert.showAndWait();

        return alert.getResult() == ButtonType.OK;
    }
}
