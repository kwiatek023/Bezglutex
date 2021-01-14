package layout.controllers;

import database.connection.SessionManager;
import database.entities.OrdersEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import layout.App;
import layout.communication.ControllerCommunicator;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.io.IOException;

public class SalesmanViewController {
    private final ObservableList<OrdersEntity> ordersEntities = FXCollections.observableArrayList();
    private SessionManager sessionManager;

    @FXML
    public BorderPane browserBorderPane;

    @FXML
    public TableColumn<OrdersEntity, Integer> id;

    @FXML
    public TableColumn<OrdersEntity, String> date;

    @FXML
    public TableColumn<OrdersEntity, String> payment;

    @FXML
    public TableView<OrdersEntity> tableView;

    @FXML
    public TableColumn<OrdersEntity, String> realized;

    @FXML
    public void initialize() {
        this.sessionManager = SessionManager.getInstance();
        createTableView();
    }

    public void createTableView() {
        tableView.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    String msg = "" + newValue.getOrderId();
                    ControllerCommunicator.getInstance().setMsg(msg);
                    App.setRoot("orderDetailsView");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Session session = sessionManager.openSession();
        Query<OrdersEntity> query = session.createQuery("FROM OrdersEntity ", OrdersEntity.class);
        ordersEntities.addAll(query.getResultList());
        sessionManager.closeSession();

        tableView.setItems(ordersEntities);

        id.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        payment.setCellValueFactory(new PropertyValueFactory<>("payment"));
        realized.setCellValueFactory(new PropertyValueFactory<>("realized"));
    }
}
