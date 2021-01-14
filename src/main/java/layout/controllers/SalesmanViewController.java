package layout.controllers;

import database.connection.SessionManager;
import database.entities.OrdersEntity;
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

public class SalesmanViewController extends AbstractBrowserController {
    private final ObservableList<OrdersEntity> ordersEntities = FXCollections.observableArrayList();

    @FXML
    public TableView<OrdersEntity> tableView;

    @FXML
    public TableColumn<OrdersEntity, String> realized;

    @FXML
    @Override
    public void initialize() {
        this.sessionManager = SessionManager.getInstance();
        createTableView();
    }

    @Override
    public void createTableView() {
        tableView.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    App.setRoot("orderDetailsView");
                } catch (IOException e) {
                    e.printStackTrace();
                }
//        showDetails(newValue.getOrderId());
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

//  private void showDetails(int orderId) {
//    try {
//      FXMLLoader loader = new FXMLLoader(App.class.getResource("orderDetailsView.fxml"));
//      final Parent parent = FXMLLoader.load(App.class.getResource("orderDetailsView.fxml"));
//      Stage stage = App.getStage();
//      stage.setScene(new Scene(parent));
//      OrderDetailsViewController controller = loader.getController();
//      controller.setOrderId(orderId);
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//  }


}
