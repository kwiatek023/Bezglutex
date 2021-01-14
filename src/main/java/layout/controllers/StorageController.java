package layout.controllers;

import database.connection.SessionManager;
import database.entities.StorageEntity;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import layout.App;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.io.IOException;
import java.math.BigDecimal;

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
//                try {
//                    String msg = "" + newValue.getSupplyId();
//                    ControllerCommunicator.getInstance().setMsg(msg);
//                    App.setRoot("supplyDetailsView");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
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

    public void returnClicked(ActionEvent actionEvent) {
        try {
            App.setRoot("storeKeeperView");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
