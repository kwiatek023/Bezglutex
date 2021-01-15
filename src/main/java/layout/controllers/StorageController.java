package layout.controllers;

import database.connection.SessionManager;
import database.entities.StorageEntity;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Window;
import layout.App;
import layout.communication.ControllerCommunicator;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;

public class StorageController {
    private SessionManager sessionManager;
    private final ObservableList<StorageEntity> storageEntities = FXCollections.observableArrayList();

    @FXML
    public TableView<StorageEntity> tableView;

    @FXML
    public TableColumn<StorageEntity, Integer> productId;

    @FXML
    public TableColumn<StorageEntity, String> type;

    @FXML
    public TableColumn<StorageEntity, BigDecimal> price;

    @FXML
    public TableColumn<StorageEntity, Integer> quantity;

    @FXML
    public void initialize() {
        this.sessionManager = SessionManager.getInstance();
        createTableView();
    }

    public void createTableView() {
        tableView.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                if(newValue != null) {
                    showDetails(newValue);
                }
            }
        });

        Session session = sessionManager.openSession();
        Query<StorageEntity> query = session.createQuery("FROM StorageEntity", StorageEntity.class);
        storageEntities.addAll(query.getResultList());
        sessionManager.closeSession();

        tableView.setItems(storageEntities);

        productId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        type.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getProductsEntity().getType().toString()));
        price.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getProductsEntity().getPrice()));
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    }

    private void showDetails(StorageEntity newValue) {
        Dialog<ButtonType> dialog = new Dialog<>();

        dialog.setWidth(300);
        dialog.setHeight(300);
        dialog.setTitle("Product details");
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

        Window window = dialog.getDialogPane().getScene().getWindow();
        window.setOnCloseRequest(event -> window.hide());

        String msg = newValue.getProductsEntity().getProductId() + " " + newValue.getProductsEntity().getType();
        ControllerCommunicator.getInstance().setMsg(msg);

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(App.class.getResource("productView.fxml"));

        try {
            Parent dialogContent = fxmlLoader.load();
            dialog.getDialogPane().setContent(dialogContent);
        } catch (IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
        }

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.CLOSE) {
            window.hide();
        }
    }

    public void returnClicked(ActionEvent actionEvent) {
        try {
            App.setRoot("storeKeeperView");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
