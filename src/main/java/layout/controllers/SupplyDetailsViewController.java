package layout.controllers;

import database.connection.SessionManager;
import database.entities.ProductsEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import layout.communication.ControllerCommunicator;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.math.BigDecimal;

public class SupplyDetailsViewController {
    private SessionManager sessionManager;
    private final ObservableList<ProductsEntity> productsEntities = FXCollections.observableArrayList();

    @FXML
    public TableColumn<ProductsEntity, Integer> productId;

    @FXML
    public TableColumn<ProductsEntity, String> type;

    @FXML
    public TableColumn<ProductsEntity, BigDecimal> price;

    @FXML
    public TableView<ProductsEntity> tableView;

    @FXML
    public void initialize() {
        sessionManager = SessionManager.getInstance();

        String msg = ControllerCommunicator.getInstance().getMsg();

        int supplyId = Integer.parseInt(msg);

        Session session = sessionManager.openSession();
        Query<ProductsEntity> query = session.createQuery("SELECT productsEntity FROM SuppliesProductsEntity sp WHERE sp.supplyId = (:supplyId)", ProductsEntity.class).
                setParameter("supplyId", supplyId);
        productsEntities.addAll(query.getResultList());
        sessionManager.closeSession();

        tableView.setItems(productsEntities);

        productId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
