package layout.controllers;

import database.connection.SessionManager;
import database.entities.SuppliesEntity;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import layout.App;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.io.IOException;

public class StoreKeeperViewController {
    private SessionManager sessionManager;
    private final ObservableList<SuppliesEntity> suppliesEntities = FXCollections.observableArrayList();

    @FXML
    public TableView<SuppliesEntity> tableView;

    @FXML
    public TableColumn<SuppliesEntity, Integer> id;

    @FXML
    public TableColumn<SuppliesEntity, String> date;

    @FXML
    public TableColumn<SuppliesEntity, String> payment;

    @FXML
    public TableColumn<SuppliesEntity, String> supplierName;

    @FXML
    public void initialize() {
        this.sessionManager = SessionManager.getInstance();
        createTableView();
    }


    public void createTableView() {
        tableView.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    App.setRoot("supplyDetailsView");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Session session = sessionManager.openSession();
        Query<SuppliesEntity> query = session.createQuery("FROM SuppliesEntity ", SuppliesEntity.class);
        suppliesEntities.addAll(query.getResultList());
        sessionManager.closeSession();

        tableView.setItems(suppliesEntities);

        id.setCellValueFactory(new PropertyValueFactory<>("supplyId"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        payment.setCellValueFactory(new PropertyValueFactory<>("payment"));
        supplierName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSuppliersEntity().getName()));
    }
}
