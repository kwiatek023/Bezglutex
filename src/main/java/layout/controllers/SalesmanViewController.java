package layout.controllers;

import database.connection.SessionManager;
import database.entities.OrdersEntity;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import layout.App;
import layout.communication.ControllerCommunicator;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.jupiter.api.Order;

import java.io.IOException;

public class SalesmanViewController {
    private final ObservableList<OrdersEntity> ordersEntities = FXCollections.observableArrayList();

    private SessionManager sessionManager;

    @FXML
    public Label findOrderLabel;

    @FXML
    public TextField orderNumberTextField;

    @FXML
    public TableView<OrdersEntity> tableView;

    @FXML
    public BorderPane browserBorderPane;

    @FXML
    public TableColumn<OrdersEntity, Integer> id;

    @FXML
    public TableColumn<OrdersEntity, String> date;

    @FXML
    public TableColumn<OrdersEntity, String> payment;

    @FXML
    public TableColumn<OrdersEntity, String> realized;

    @FXML
    public TableColumn<OrdersEntity, String> firstname;

    @FXML
    public TableColumn<OrdersEntity, String> lastname;

    @FXML
    public void initialize() {
        this.sessionManager = SessionManager.getInstance();
        createTableView();
    }

    public void createTableView() {
        tableView.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
                showDetails(newValue);
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
        firstname.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomersEntity().getFirstname()));
        lastname.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomersEntity().getLastname()));

    }

    private void showDetails(OrdersEntity newValue) {
        if (newValue != null) {
            try {
                String msg = "" + newValue.getOrderId();
                ControllerCommunicator.getInstance().setMsg(msg);
                App.setRoot("orderDetailsView");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            displayErrorAlert("No order found.");
        }
    }


    public void findOrderButtonClicked(ActionEvent actionEvent) {
        int orderId = 0;
        OrdersEntity order = null;

        if (!orderNumberTextField.getText().isBlank()) {
            try {
                orderId = Integer.parseInt(orderNumberTextField.getText());
            } catch (NumberFormatException ex) {
                displayErrorAlert("Wrong order number");
                return;
            }

            for (OrdersEntity ordersEntity: ordersEntities) {
                if (ordersEntity.getOrderId() == orderId) {
                    order = ordersEntity;
                    orderId = ordersEntity.getOrderId();
                    break;
                }
            }
        }

        if (orderId > 0) {
            showDetails(order);
            return;
        }

        displayErrorAlert("No order found.");
    }

    private void displayErrorAlert(String errorMsg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText(errorMsg);
        alert.showAndWait();
    }
}
