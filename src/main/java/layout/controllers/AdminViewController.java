package layout.controllers;

import database.UserType;
import database.connection.SessionManager;
import database.entities.UsersEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class AdminViewController {
    private final ObservableList<UsersEntity> usersEntities = FXCollections.observableArrayList();

    @FXML
    public BorderPane borderPane;

    @FXML
    public TableView tableView;

    @FXML
    public TableColumn idColumn;

    @FXML
    public TableColumn loginColumn;

    @FXML
    public TableColumn typeColumn;

    @FXML
    public ChoiceBox<UserType> userTypeChoiceBox;

//    public Button addUserButton;
//    public TableColumn removeButtonsColumn;


//    public TextField loginTextField;
//    public PasswordField passwordField;
//    public Button removeButton = new Button("REMOVE");

    public void initialize() {
        SessionManager sessionManager = SessionManager.getInstance();

        tableView.prefWidthProperty().bind(borderPane.widthProperty().divide(1.5));
        idColumn.prefWidthProperty().bind(tableView.widthProperty().divide(4));
        loginColumn.prefWidthProperty().bind(tableView.widthProperty().divide(4));
        typeColumn.prefWidthProperty().bind(tableView.widthProperty().divide(4));

        userTypeChoiceBox.getItems().addAll(UserType.values());

        Session session = sessionManager.getCurrentSession();
        Query query = session.createQuery("FROM UsersEntity");
        usersEntities.addAll(query.getResultList());
        tableView.setItems(usersEntities);

        //tableView.setEditable(true);
        idColumn.setCellValueFactory(new PropertyValueFactory<UsersEntity, Integer>("userId"));
        loginColumn.setCellValueFactory(new PropertyValueFactory<UsersEntity, String>("login"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<UsersEntity, String>("type"));
    }
//
//    private Callback<TableColumn<UsersEntity, Void>, TableCell<UsersEntity, Void>> createButtonCellFactory() {
//        return new Callback<TableColumn<UsersEntity, Void>, TableCell<UsersEntity, Void>>() {
//            @Override
//            public TableCell<UsersEntity, Void> call(TableColumn<UsersEntity, Void> param) {
//                return new TableCell<UsersEntity, Void>() {
//                    private Button button = new Button("REMOVE");
//
//                    {
//                        setAlignment(Pos.CENTER);
//                        button.setOnAction((event) -> {
//                            removeUser(data.get(getIndex()));
//                        });
//                    }
//
//                    @Override
//                    public void updateItem(Void item, boolean empty) {
//                        super.updateItem(item, empty);
//                        if (empty) {
//                            setGraphic(null);
//                        } else {
//                            setGraphic(button);
//                        }
//                    }
//                };
//            }
//        };
//    }
//
//    private void removeUser(UsersEntity user) {
//        if (showConfirmationAlert("Are you sure you want to remove user " +
//                user.getUserId() + "?\nThis cannot be undone!")) {
//            //TODO handle deleting oneself
//            try (Session session = LoginManager.getSession()) {
//                session.beginTransaction();
//                session.remove(user);
//                session.getTransaction().commit();
//                data.remove(user);
//            } catch (OptimisticLockException exception) {
//                showErrorAlert("Unable to remove user '" + user.getUserId() + "'.\nUser is present in the logs.\n" +
//                        "To remove this user clean the logs.");
//            }
//        }
//    }
//
//    private void showUsersInTable(List<UsersEntity> users) {
//        data.addAll(users);
//    }
//
//    @FXML
//    public void goBack() {
//        TileView.initialize(LoginManager.getAccessLevel());
//    }
//
//    public void addUserButtonClicked(ActionEvent actionEvent) {
//        if (loginTextField.getText().isEmpty() || passwordField.getText().isEmpty() || accessLevelChoiceBox.getValue() == null) {
//            showErrorAlert("Login, password and access level cannot be empty!");
//            return;
//        }
//        for (UsersEntity user : data) {
//            if (user
//                    .getUserId().equals(loginTextField.getText())) {
//                showErrorAlert("User with " + loginTextField.getText() + " already exists!");
//                return;
//            }
//        }
//        UsersEntity user = new UsersEntity();
//        user.setUserId(loginTextField.getText());
//        user.setPassword(BCrypt.hashpw(passwordField.getText(), BCrypt.gensalt()));
//        user.setAccessLevel(accessLevelChoiceBox.getValue());
//        data.add(user);
//        Session session = LoginManager.getSession();
//        session.beginTransaction();
//        session.save(user);
//        session.getTransaction().commit();
//        session.close();
//
//    }
//
//    private void showErrorAlert(String mess) {
//        Alert alert = new Alert(Alert.AlertType.ERROR);
//        alert.setHeaderText(null);
//        alert.setContentText(mess);
//        alert.showAndWait();
//    }
//
//    private boolean showConfirmationAlert(String message) {
//        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//        alert.setHeaderText(null);
//        alert.setContentText(message);
//        return !alert.showAndWait().get().getButtonData().isCancelButton();
//    }

    public void addUser(ActionEvent actionEvent) {
    }

    public void resetPassword(ActionEvent actionEvent) {
    }

    public void removeUser(ActionEvent actionEvent) {
    }
}

